/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.help.Order;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumPopulationPriority;
import com.syncnapsis.mock.MockConnection;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.providers.ConnectionProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.data.ExtendedRandom;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.service.rpc.RPCService;

@TestExcludesMethods({ "*etSolarSystemPopulationManager", "*etSolarSystemInfrastructureManager", "afterPropertiesSet" })
public class ConquestManagerImplTest extends BaseSpringContextTestCase
{
	private SessionProvider		sessionProvider;
	private BaseGameManager		securityManager;
	private ConnectionProvider	connectionProvider;

	private static final String	beanName	= "conquestManager";

	@TestCoversMethods({ "createChannel", "getChannels" })
	public void testCreateChannel()
	{
		ConquestManagerImpl conquestManager = new ConquestManagerImpl();
		conquestManager.setConnectionProvider(connectionProvider);
		conquestManager.setSecurityManager(securityManager);
		conquestManager.setBeanName(beanName);

		final String chA = "chA";
		final String chB = "chB";

		int valA = 1234;
		int valB = 5678;

		// check channel list (empty)
		assertEquals(0, conquestManager.getChannels().size());

		// check there is no initial value
		assertEquals(null, conquestManager.getLastValue(chA));
		assertEquals(null, conquestManager.getLastValue(chB));

		// create channels
		assertTrue(conquestManager.createChannel(chA, valA));
		assertTrue(conquestManager.createChannel(chB, valB));

		// check channel list (2 channels)
		assertEquals(2, conquestManager.getChannels().size());
		assertTrue(conquestManager.getChannels().contains(chA));
		assertTrue(conquestManager.getChannels().contains(chB));

		// check initial value
		assertEquals(valA, conquestManager.getLastValue(chA));
		assertEquals(valB, conquestManager.getLastValue(chB));
	}

	@TestCoversMethods({ "subscribe", "unsubscribe", "isUnderSubscription", "getSubscribedChannels" })
	public void testSubscribe()
	{
		ConquestManagerImpl conquestManager = new ConquestManagerImpl();
		conquestManager.setConnectionProvider(connectionProvider);
		conquestManager.setSecurityManager(securityManager);
		conquestManager.setBeanName(beanName);

		sessionProvider.set(new MockHttpSession());

		final RPCService mockRPCService = mockContext.mock(RPCService.class);
		conquestManager.setRpcService(mockRPCService);

		final Connection con1 = new MockConnection();
		final ConquestManager client1ConquestManager = mockContext.mock(ConquestManager.class);

		final String chA = "chA";
		final String chB = "chB";

		final int valA = 1234;
		final int valB = 5678;

		connectionProvider.set(con1);

		// set Expectations for getClientInstance(..)

		// channels not existing
		assertFalse(conquestManager.isUnderSubscription(chA));
		assertFalse(conquestManager.isUnderSubscription(chB));
		assertEquals(0, conquestManager.getSubscribedChannels().size());
		assertFalse(conquestManager.getSubscribedChannels().contains(chA));
		assertFalse(conquestManager.getSubscribedChannels().contains(chB));

		assertFalse(conquestManager.subscribe(chA));
		assertFalse(conquestManager.subscribe(chB));

		// create channels (tested separately above)
		assertTrue(conquestManager.createChannel(chA, valA));
		assertTrue(conquestManager.createChannel(chB, valB));

		// subscribe chA
		mockContext.checking(new Expectations() {
			{
				oneOf(mockRPCService).getClientInstance(beanName, con1);
				will(returnValue(client1ConquestManager));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(client1ConquestManager).update(chA, valA);
			}
		});
		assertTrue(conquestManager.subscribe(chA));

		mockContext.assertIsSatisfied();
		assertTrue(conquestManager.isUnderSubscription(chA));
		assertFalse(conquestManager.isUnderSubscription(chB));
		assertEquals(1, conquestManager.getSubscribedChannels().size());
		assertTrue(conquestManager.getSubscribedChannels().contains(chA));
		assertFalse(conquestManager.getSubscribedChannels().contains(chB));

		// subscribe chA (again)
		assertFalse(conquestManager.subscribe(chA));

		assertTrue(conquestManager.isUnderSubscription(chA));
		assertFalse(conquestManager.isUnderSubscription(chB));
		assertEquals(1, conquestManager.getSubscribedChannels().size());
		assertTrue(conquestManager.getSubscribedChannels().contains(chA));
		assertFalse(conquestManager.getSubscribedChannels().contains(chB));

		// subscribe chB
		mockContext.checking(new Expectations() {
			{
				oneOf(mockRPCService).getClientInstance(beanName, con1);
				will(returnValue(client1ConquestManager));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(client1ConquestManager).update(chB, valB);
			}
		});
		assertTrue(conquestManager.subscribe(chB));

		mockContext.assertIsSatisfied();
		assertTrue(conquestManager.isUnderSubscription(chA));
		assertTrue(conquestManager.isUnderSubscription(chB));
		assertEquals(2, conquestManager.getSubscribedChannels().size());
		assertTrue(conquestManager.getSubscribedChannels().contains(chA));
		assertTrue(conquestManager.getSubscribedChannels().contains(chB));

		// unsubscribe chA
		assertTrue(conquestManager.unsubscribe(chA));

		assertFalse(conquestManager.isUnderSubscription(chA));
		assertTrue(conquestManager.isUnderSubscription(chB));
		assertEquals(1, conquestManager.getSubscribedChannels().size());
		assertFalse(conquestManager.getSubscribedChannels().contains(chA));
		assertTrue(conquestManager.getSubscribedChannels().contains(chB));

		// unsubscribe chA (again)
		assertFalse(conquestManager.unsubscribe(chA));

		assertFalse(conquestManager.isUnderSubscription(chA));
		assertTrue(conquestManager.isUnderSubscription(chB));
		assertEquals(1, conquestManager.getSubscribedChannels().size());
		assertFalse(conquestManager.getSubscribedChannels().contains(chA));
		assertTrue(conquestManager.getSubscribedChannels().contains(chB));

		// unsubscribe chB
		assertTrue(conquestManager.unsubscribe(chB));

		assertFalse(conquestManager.isUnderSubscription(chA));
		assertFalse(conquestManager.isUnderSubscription(chB));
		assertEquals(0, conquestManager.getSubscribedChannels().size());
		assertFalse(conquestManager.getSubscribedChannels().contains(chA));
		assertFalse(conquestManager.getSubscribedChannels().contains(chB));
	}

	@TestCoversMethods({ "update", "pushUpdate", "getLastValue" })
	public void testUpdate()
	{
		ConquestManagerImpl conquestManager = new ConquestManagerImpl();
		conquestManager.setConnectionProvider(connectionProvider);
		conquestManager.setSecurityManager(securityManager);
		conquestManager.setBeanName(beanName);

		sessionProvider.set(new MockHttpSession());

		final RPCService mockRPCService = mockContext.mock(RPCService.class);
		conquestManager.setRpcService(mockRPCService);

		final ConquestManager client1ConquestManager = mockContext.mock(ConquestManager.class, "client1ConquestManager");
		final ConquestManager client2ConquestManager = mockContext.mock(ConquestManager.class, "client2ConquestManager");
		final ConquestManager client3ConquestManager = mockContext.mock(ConquestManager.class, "client3ConquestManager");

		final String conquestManagerName = "conquestManager";

		final Connection con1 = new MockConnection();
		final Connection con2 = new MockConnection();
		final Connection con3 = new MockConnection();

		final String chA = "chA";
		final String chB = "chB";

		final int[] valA = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		final int[] valB = { 9, 8, 7, 6, 5, 4, 3, 2, 1 };

		conquestManager.createChannel(chA, valA[0]);
		conquestManager.createChannel(chB, valB[0]);

		connectionProvider.set(con1);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockRPCService).getClientInstance(beanName, con1);
				will(returnValue(client1ConquestManager));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(client1ConquestManager).update(chA, valA[0]);
			}
		});
		conquestManager.subscribe(chA);

		connectionProvider.set(con2);
		mockContext.checking(new Expectations() {
			{
				exactly(2).of(mockRPCService).getClientInstance(beanName, con2);
				will(returnValue(client2ConquestManager));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(client2ConquestManager).update(chA, valA[0]);
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(client2ConquestManager).update(chB, valB[0]);
			}
		});
		conquestManager.subscribe(chA);
		conquestManager.subscribe(chB);

		connectionProvider.set(con3);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockRPCService).getClientInstance(beanName, con3);
				will(returnValue(client3ConquestManager));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(client3ConquestManager).update(chB, valB[0]);
			}
		});
		conquestManager.subscribe(chB);

		// check channel chA
		// -> subscribers: con1, con2
		for(int i = 1; i < valA.length; i++)
		{
			final int fi = i;
			mockContext.checking(new Expectations() {
				{
					oneOf(mockRPCService).getClientInstance(conquestManagerName, con1);
					will(returnValue(client1ConquestManager));
				}
			});
			mockContext.checking(new Expectations() {
				{
					oneOf(mockRPCService).getClientInstance(conquestManagerName, con2);
					will(returnValue(client2ConquestManager));
				}
			});
			mockContext.checking(new Expectations() {
				{
					oneOf(client1ConquestManager).update(chA, valA[fi]);
				}
			});
			mockContext.checking(new Expectations() {
				{
					oneOf(client2ConquestManager).update(chA, valA[fi]);
				}
			});

			conquestManager.update(chA, valA[fi]);

			assertEquals(valA[fi], conquestManager.getLastValue(chA));
			mockContext.assertIsSatisfied();
		}

		// check channel chB
		// -> subscribers: con2, con3
		for(int i = 1; i < valB.length; i++)
		{
			final int fi = i;
			mockContext.checking(new Expectations() {
				{
					oneOf(mockRPCService).getClientInstance(conquestManagerName, con2);
					will(returnValue(client2ConquestManager));
				}
			});
			mockContext.checking(new Expectations() {
				{
					oneOf(mockRPCService).getClientInstance(conquestManagerName, con3);
					will(returnValue(client3ConquestManager));
				}
			});
			mockContext.checking(new Expectations() {
				{
					oneOf(client2ConquestManager).update(chB, valB[fi]);
				}
			});
			mockContext.checking(new Expectations() {
				{
					oneOf(client3ConquestManager).update(chB, valB[fi]);
				}
			});

			conquestManager.update(chB, valB[fi]);

			assertEquals(valB[fi], conquestManager.getLastValue(chB));
			mockContext.assertIsSatisfied();
		}
	}

	public void testSendTroops() throws Exception
	{
		final long referenceTime = 1234;
		
		final SolarSystemPopulationManager mockSolarSystemPopulationManager = mockContext.mock(SolarSystemPopulationManager.class);
		final SolarSystemInfrastructureManager mockSolarSystemInfrastructureManager = mockContext.mock(SolarSystemInfrastructureManager.class);
		final TimeProvider mockTimeProvider = new MockTimeProvider(referenceTime);
		final BaseGameManager mockSecurityManager = new BaseGameManager(securityManager);
		mockSecurityManager.setTimeProvider(mockTimeProvider);
		
		ConquestManagerImpl conquestManager = new ConquestManagerImpl();
		conquestManager.setSolarSystemPopulationManager(mockSolarSystemPopulationManager);
		conquestManager.setSolarSystemInfrastructureManager(mockSolarSystemInfrastructureManager);
		conquestManager.setSecurityManager(securityManager);

		ExtendedRandom random = new ExtendedRandom(1234);

		final List<Order> orders = new ArrayList<Order>();
		final Map<Long, SolarSystemPopulation> populations = new HashMap<Long, SolarSystemPopulation>();
		final Map<Long, SolarSystemInfrastructure> infrastructures = new HashMap<Long, SolarSystemInfrastructure>();

		// @formatter:off
		final Empire empire1 = new Empire(); empire1.setId(1L);
		final Empire empire2 = new Empire(); empire2.setId(2L);
		final Participant participant1 = new Participant(); participant1.setId(1L); participant1.setEmpire(empire1);
		final Participant participant2 = new Participant(); participant2.setId(2L); participant2.setEmpire(empire2);
		// @formatter:on
		
		//
		empire1.setPlayer(new Player());
		empire1.getPlayer().setUser(new User());
		empire1.getPlayer().getUser().setUsername("test");
		
		HttpSession session = new MockHttpSession();
		securityManager.getSessionProvider().set(session);
		securityManager.getEmpireProvider().set(empire1);

		for(int i = 1; i <= 3; i++)
		{
			Order order = new Order();
			order.setOriginId(100L + i);
			order.setTargetId(200L + i);
			order.setPopulation(i * 1000L);
			order.setTravelSpeed(i * 100);
			order.setExodus(i % 2 == 0);
			order.setAttackPriority(random.nextEnum(EnumPopulationPriority.class));
			order.setBuildPriority(random.nextEnum(EnumPopulationPriority.class));
			orders.add(order);
			
			SolarSystemPopulation population1 = new SolarSystemPopulation();
			population1.setParticipant(participant1);
			population1.setColonizationDate(new Date(referenceTime-100*i));
			population1.setActivated(true);
			SolarSystemPopulation population2 = new SolarSystemPopulation();
			population2.setParticipant(participant2);
			population2.setColonizationDate(new Date(referenceTime-100*i));
			population2.setActivated(true);
			
			SolarSystemInfrastructure originInfrastructure = new SolarSystemInfrastructure();
			originInfrastructure.setPopulations(Arrays.asList(population1, population2));
			
			SolarSystemInfrastructure targetInfrastructure = new SolarSystemInfrastructure();
			targetInfrastructure.setPopulations(new ArrayList<SolarSystemPopulation>());

			populations.put(order.getOriginId(), population1);
			infrastructures.put(order.getOriginId(), originInfrastructure);
			infrastructures.put(order.getTargetId(), targetInfrastructure);
		}

		for(Order o : orders)
		{
			final Order order = o;
			mockContext.checking(new Expectations() {
				{
					oneOf(mockSolarSystemInfrastructureManager).get(order.getOriginId());
					will(returnValue(infrastructures.get(order.getOriginId())));
				}
			});
			mockContext.checking(new Expectations() {
				{
					oneOf(mockSolarSystemInfrastructureManager).get(order.getTargetId());
					will(returnValue(infrastructures.get(order.getTargetId())));
				}
			});
			//@formatter:off
			if(o.isExodus())
			{
				mockContext.checking(new Expectations() {
					{
						oneOf(mockSolarSystemPopulationManager).resettle(
								with(same(populations.get(order.getOriginId()))),
								with(same(infrastructures.get(order.getTargetId()))),
								with(equal(order.getTravelSpeed())),
								with(equal(true)),
								with(equal(order.getAttackPriority())),
								with(equal(order.getBuildPriority()))
							);
						will(returnValue(new SolarSystemPopulation()));
					}
				});
			}
			else
			{
				mockContext.checking(new Expectations() {
					{
						oneOf(mockSolarSystemPopulationManager).spinoff(
								with(same(populations.get(order.getOriginId()))),
								with(same(infrastructures.get(order.getTargetId()))),
								with(equal(order.getTravelSpeed())),
								with(equal(order.getPopulation())),
								with(equal(order.getAttackPriority())),
								with(equal(order.getBuildPriority()))
							);
						will(returnValue(new SolarSystemPopulation()));
					}
				});
			}
			//@formatter:on
		}

		int result = conquestManager.sendTroops(orders);
		mockContext.assertIsSatisfied();
		
		assertEquals(orders.size(), result);
	}
}
