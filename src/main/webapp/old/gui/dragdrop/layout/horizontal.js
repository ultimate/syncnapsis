function horizontalLayout() {
	this.reorder = function() {
		var lineWidth = 0;
		var lineHeight = 0;	
		var fieldWidth = getWidth(this);
		var fieldHeight = getWidth(this);	
		var totalDim = 0;
		var el = this.firstElement;
		for	(var i=0; i<this.size; i++) {
			if(el.parentNode != null) {
				if(el.parentNode != this) {
					el.parentNode.removeChild(el);
					this.appendChild(el);
				}
			}
			else {
				this.appendChild(el);
			}

			if(this.autoResize == false && fieldWidth < 2*this.borderX + lineWidth + getWidth(el)) {
				totalDim += lineHeight+this.gapY;
				lineHeight = 0;
				lineWidth = 0;
				if(this.multiline == false)
					return false;
			}
				
			if(lineHeight < getHeight(el)) 
				lineHeight = getHeight(el);
			
			setX(el, this.borderX+lineWidth);
			setY(el, this.borderY+totalDim);
			lineWidth += this.gapX+getWidth(el);

			el = el.nextElement;
		}
		if(this.autoResize == true) {
			totalDim += lineWidth+this.borderX;
			setWidth(this, totalDim);		
		}		
		return true;
	};
	
	this.getDropFieldInsertPosition = function(e) {
		var x = getMouseX(e);
		var y = getMouseY(e);	
		var lineTop = 0;
		var lineTopOld = -1;
		var lineHeight = 0;
		var i = 0;
		var bestI = 0;
		var el;
		var lineBeginning = null;
		
		if(this.size == 0)
			return {"pos": 0, "lineBegin": true};
	
		for( el = this.firstElement; el != null; el = el.nextElement ) {
			var lineTop = getYAbs(el);

			if(lineTopOld != lineTop) {
				if(y < lineTopOld+lineHeight) {
					el = el.prevElement;
					break;
				}	
				lineBeginning = el;
				lineTopOld = lineTop;
				lineHeight = 0;
			}	
			
			if(getHeight(el) > lineHeight)
				lineHeight = getHeight(el);
				
			i++;
		}
		if(el == null) 
			el = this.lastElement;
	
		if((x > (getXAbs(el) + (getWidth(el)/2)))) 
			return {"pos": i, "lineBegin": false};
		
		if(lineHeight+getYAbs(el) < y &&this.multiline == true)
			return {"pos": this.size, "lineBegin": false};
			
		var insertEl = null;	
		for(; el != lineBeginning.prevElement; el = el.prevElement) {
			i--;		
			if(x < getXAbs(el)+(getWidth(el)/2)) {
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
			this.insertPos.style.backgroundColor = "#000000";		
			setWidth(this.insertPos, 1);		
		}

		setHeight(this.insertPos, getHeight(element));	
	
		if(pos == 0) {
			setX(this.insertPos, this.borderX/2);
			setY(this.insertPos, this.borderY);		
		}	
		else {
			var el = this.firstElement;
			for(var i = 0; i<pos-1 && el != this.lastElement; i++) {
				el = el.nextElement;
			}
			if(lineBegin == true) {
				setX(this.insertPos, this.borderX/2);
				setY(this.insertPos, getY(el.nextElement));		
			}
			else {
				setX(this.insertPos, getX(el)+getWidth(el)+(this.gapX-getWidth(this.insertPos))/2);
				setY(this.insertPos, getY(el));		
			}
		}
		show(this.insertPos);
	};
	
	this.layoutFull = function() {
		
		var useableHeight = getHeight(this)-2*this.borderY;
		var useableWidth = getWidth(this)-2*this.borderX;
		
		if( (getY(this.lastElement) + getHeight(this.lastElement) - this.borderY) > useableHeight)
			return true;			
		if( (getX(this.lastElement) + getWidth(this.lastElement) - this.borderY) > useableWidth)
			return true;		
		
		return false;
	};

}