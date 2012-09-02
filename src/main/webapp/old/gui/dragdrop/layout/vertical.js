function verticalLayout() {
	this.reorder = function() {
		var lineWidth = 0;
		var lineHeight = 0;	
		var fieldWidth = getWidth(this);
		var fieldHeight = getWidth(this);	
		var totalDim = 0;
		info("vor der schleife");
		var el = this.firstElement;
		for	(var i=0; i<this.size; i++) {
			error("schleifendurchlauf");
			if(el.parentNode != null) {
				if(el.parentNode != this) {
					el.parentNode.removeChild(el);
					this.appendChild(el);
				}
			} else {
				this.appendChild(el);
			}
			if(this.autoResize == false && fieldHeight < 2*this.borderY + lineHeight + getHeight(el)) {
				totalDim += lineWidth+this.gapX;
				lineHeight = 0;
				lineWidth = 0;
				if(this.multiline == false)
					return false;
				
			}
				
			if(lineWidth < getWidth(el)) 
				lineWidth = getWidth(el);

			warn(el.id + " - " + this.borderY+lineHeight);				
			info(this.elementMoved);
			if(this.elementMoved == false && this.smoothInsert == true && ((this.borderY+lineHeight == getY(el)) ^ ((this.borderX+totalDim == getX(el))))) 
			{
				error("elementMoved = false");
				setY(el, this.borderY+lineHeight, 200);
				setX(el, this.borderX+totalDim);
			}
			else {
				setY(el, this.borderY+lineHeight);
				setX(el, this.borderX+totalDim);
			}
			
			lineHeight += this.gapY+getHeight(el);
		

			el = el.nextElement;
		}
		info("nach der schleife");
		if(this.autoResize == true) {
			lineHeight += 2*this.borderY-this.gapY;
			setHeight(this, lineHeight);		
		}
		return true;
	};
	
	this.getDropFieldInsertPosition = function(e) {
		var x = getMouseX(e);
		var y = getMouseY(e);	
		var lineLeft = 0;
		var lineLeftOld = -1;
		var lineWidth = 0;
		var i = 0;
		var bestI = 0;
		var el;
		var lineBeginning = null;
	
		if(this.size == 0)
			return {"pos": 0, "lineBegin": true};

		for( el = this.firstElement; el != null; el = el.nextElement ) {
			var lineLeft = getXAbs(el);

			if(lineLeftOld != lineLeft) {
				if(x < lineLeftOld+lineWidth) {
					el = el.prevElement;
					break;
				}	
				lineBeginning = el;
				lineLeftOld = lineLeft;
				lineWidth = 0;
			}	

			if(getWidth(el) > lineWidth)
				lineWidth = getWidth(el);
				
			i++;
		}
		if(el == null) 
			el = this.lastElement;
	
		if((y > (getYAbs(el) + (getHeight(el)/2)))) 
			return {"pos": i, "lineBegin": false};


		if(lineWidth+getXAbs(el) < x && this.multiline == true)
			return {"pos": this.size, "lineBegin": false};
			
		var insertEl = null;	
		for(; el != lineBeginning.prevElement; el = el.prevElement) {
			i--;		
			if(y < getYAbs(el)+(getHeight(el)/2)) {
				bestI = i;
				insertEl = el;
			}			
		}

		if(insertEl == lineBeginning)
			return {"pos": bestI, "lineBegin": true};
	
		return {"pos": bestI, "lineBegin": false};
	};

	
	this.showInsertPosInDropField = function(pos, element, lineBegin) {
		hideAllInsertPositions();
    	if(this.insertPos == null) {
			this.insertPos = document.createElement("div");
			this.insertPos.style.position = "absolute";
			this.appendChild(this.insertPos);
			this.insertPos.style.backgroundColor = "#00FFFF";		
			setHeight(this.insertPos, 1);		
		}

		setWidth(this.insertPos, getWidth(element));	
	
		if(pos == 0) {
			setX(this.insertPos, this.borderX);
			setY(this.insertPos, this.borderY/2);		
		}	
		else {
			var el = this.firstElement;
			for(var i = 0; i<pos-1 && el != this.lastElement; i++) {
				el = el.nextElement;
			}
			if(el == this.lastElement) {
					setY(this.insertPos, getY(el)+getHeight(el)+(this.borderY-getHeight(this.insertPos))/2);
					setX(this.insertPos, getX(el));							
			} else {
				if(lineBegin == true) {
					setY(this.insertPos, this.borderY/2);
					setX(this.insertPos, getX(el.nextElement));		
				}
				else {
					setY(this.insertPos, getY(el)+getHeight(el)+(this.gapY-getHeight(this.insertPos))/2);
					setX(this.insertPos, getX(el));		
				}
			}
		}
		show(this.insertPos);
	};

	this.layoutFull = function() {

		var useableHeight = getHeight(this)-2*this.borderY;
		var useableWidth = getWidth(this)-2*this.borderX;
	
		if( (getY(this.lastElement) + getHeight(this.lastElement) - this.borderY) > useableHeight)
			return true;			
		if( (getX(this.lastElement) + getWidth(this.lastElement) - this.borderX) > useableWidth)
			return true;		
			
		return false;
	};

}