// BROWSER SNIFF & COMMON JS LIB - v.2.6
// COPYRIGHT SUN MICROSYSTEMS INC. 2006
// QUESTIONS? webdesign -at- sun.com

is = new ottosniff();
function ottosniff() {
	var b = navigator.appName
	if (b=="Netscape") this.b = "ns"
	else this.b = b
	this.version = navigator.appVersion
	this.v = parseInt(this.version)
	this.ns = (this.b=="ns" && this.v>=5)
	this.op = (navigator.userAgent.indexOf('Opera')>-1)
	this.safari = (navigator.userAgent.indexOf('Safari')>-1)
	this.op7 = (navigator.userAgent.indexOf('Opera')>-1 && this.v>=7 && this.v<8)
	this.op78 = (navigator.userAgent.indexOf('Opera')>-1 && this.v>=7 || navigator.userAgent.indexOf('Opera')>-1 && this.v>=8)
	this.ie5 = (this.version.indexOf('MSIE 5')>-1)
	this.ie6 = (this.version.indexOf('MSIE 6')>-1)
	this.ie7 = (this.version.indexOf('MSIE 7')>-1)
	this.ie56 = (this.ie5||this.ie6)
	this.iewin = (this.ie56 && navigator.userAgent.indexOf('Windows')>-1 || this.ie7 && navigator.userAgent.indexOf('Windows')>-1)
	this.iemac = (this.ie56 && navigator.userAgent.indexOf('Mac')>-1)
	this.moz = (navigator.userAgent.indexOf('Mozilla')>-1)
	this.ff = (navigator.userAgent.indexOf('Firefox')>-1)
	this.moz13 = (navigator.userAgent.indexOf('Mozilla')>-1 && navigator.userAgent.indexOf('1.3')>-1) 
	this.oldmoz = (navigator.userAgent.indexOf('Mozilla')>-1 && navigator.userAgent.indexOf('1.4')>-1 && !this.ff ||navigator.userAgent.indexOf('Mozilla')>-1 && navigator.userAgent.indexOf('1.5')>-1 && !this.ff ||navigator.userAgent.indexOf('Mozilla')>-1 && navigator.userAgent.indexOf('1.6')>-1 && !this.ff)
	this.ns6 = (navigator.userAgent.indexOf('Netscape6')>-1)
	this.docom = (this.ie56||this.ns||this.iewin||this.op||this.iemac||this.safari||this.moz||this.oldmoz||this.ns6)
}

// VARS
ptest="noprint";
var oldmenu = new Array();
var navmenu = new Array();

// ADD BROWSER CLASS TO BODY
if(is.op || is.ie56 || is.ie7 || is.iemac || is.safari){
	var bodycheck=0;
	findbody();
}
function findbody() {
	var bbod = document.getElementsByTagName('body')[0];
	if (bbod){
		if(is.op){var bclass = "browserOpera";}
		else if(is.safari){var bclass = "browserSafari";}
		else if(is.ie56){var bclass = "browserExplorer56";}
		else if(is.ie7){var bclass = "browserExplorer7";}
		else if(is.iemac){var bclass = "browserExplorerMac";}
		addClassName(bbod, bclass);
	}else if(bodycheck < 100){
		bodycheck++;
		setTimeout('findbody();',100);
	}
}

// PAGE PREP
function prepSunPage(){
	if (is.docom){
		var kdoc = document;
		// prep homepage
		if (kdoc.getElementById('newsitem2') || kdoc.getElementById('subhover2')){
			prephome();
			done = true;
		}	
		// no hardcode A2
		if (ptest.indexOf("yesprint") == -1 && ptest.indexOf("prepmenus") == -1 && document.getElementById('mtopic1') && navmenu['1.0']){
			printmenus();
			prepmenus();
		}
		// add actions to global search
		if (kdoc.getElementById('searchfield')){
			kdoc.getElementById('searchfield').onfocus = function(){
				if(kdoc.getElementById('searchfield').value==kdoc.getElementById('searchfield').defaultValue)kdoc.getElementById('searchfield').value='';
				if (!is.iemac){
					kdoc.getElementById('searchfield').style.width='110px';
				}
			};
			kdoc.getElementById('searchfield').onblur = function(){
				if(kdoc.getElementById('searchfield').value=="")kdoc.getElementById('searchfield').value=kdoc.getElementById('searchfield').defaultValue;
				if (!is.iemac){
					kdoc.getElementById('searchfield').style.width='67px'
				}
			};
		}
		// add blur action to logo
		if (kdoc.getElementById('sunlogo')){
			kdoc.getElementById('sunlogo').onfocus = function(){hideA2(0)};
		}
		// add bg spacer gif to off divs in IE for better response
		if(is.ie56 && kdoc.getElementById('offdiv') && kdoc.getElementById('offdivL') && kdoc.getElementById('offdivT') && kdoc.getElementById('offdivR') && imdir && !is.iemac){
			kdoc.getElementById('offdiv').style.background = kdoc.getElementById('offdivL').style.background = kdoc.getElementById('offdivT').style.background = kdoc.getElementById('offdivR').style.background = 'url('+imdir+'/a.gif)';
		}
		// look for dhtml pop up classes
		if (is.ie5){
 			var alltags = new Array('a','b','div','span','td','li','ul','input','select','img');
		}else{
 			var alltags = new Array('*');
		}
		for (var ivp=0;ivp<alltags.length;ivp++){
			var an = document.getElementsByTagName(alltags[ivp]);
			for (var i=0;i<an.length;i++){
				if (an[i].className.indexOf("k2over") > -1 || an[i].className.indexOf("k2cl") > -1 || an[i].className.indexOf("a2menu") > -1){
					an[i].prp = new Array(0,0,0,"","","");
					an[i].className = an[i].className.replace(/(k2over) +|(k2cl...) +/,"$1-");
					var cls = an[i].className.split(' '); 
					for (var v=0;v<cls.length;v++){
						if (cls[v].indexOf("k2over") > -1 || cls[v].indexOf("k2click-") > -1){
							var p_objs = an[i].aob = cls[v].split('-');
							if (!p_objs[2]){
								an[i].aob[2] = p_objs[2] = p_objs[1];
								an[i].aob[1] = an[i];
							}
							kpop = kdoc.getElementById(p_objs[2]);
							kpop.kp_objs = p_objs[2];						
							kpop.kp_trig = p_objs[1];						
						}else if (cls[v].indexOf("a2menu") > -1){
							an[i].aob = p_objs = new Array("a2menu","mtopic"+an[i].getAttribute('id').substring(5),an[i].getAttribute('id').substring(5));
							an[i].aob[2] = p_objs[2] = "flymenu"+an[i].aob[2];
							kpop = kdoc.getElementById(p_objs[2]);
							kpop.kp_objs = p_objs[2];
						}else if (cls[v].indexOf("k2close-") > -1){
							an[i].aob = cls[v].split('-');
						}else if (cls[v].indexOf("x") == 0){
							an[i].prp[0] = (cls[v].substring(1) * 1) + an[i].prp[0];
						}else if (cls[v].indexOf("y") == 0){
							an[i].prp[1] = (cls[v].substring(1) * 1) + an[i].prp[1];
						}else if (cls[v].indexOf("z") == 0){
							an[i].prp[2] = (cls[v].substring(1) * 1);
						}else if (cls[v].indexOf("pAbsolute") == 0){
							an[i].prp[3] = (cls[v].substring(1));
						}else if (cls[v].indexOf("vBottom") == 0 || cls[v].indexOf("vTop") == 0 || cls[v].indexOf("vMiddle") == 0){
							an[i].prp[4] = cls[v];
						}else if (cls[v].indexOf("hRight") == 0 || cls[v].indexOf("hMiddleRight") == 0 || cls[v].indexOf("hLeft") == 0 || cls[v].indexOf("hMiddleLeft") == 0 || cls[v].indexOf("hMiddle") == 0){
							an[i].prp[5] = cls[v];
						}
					}
					if (an[i].aob[0].indexOf("a2menu") > -1){
							an[i].prp[0] = an[i].prp[0] - 20;
							an[i].onmouseover = function(){
								showK2(this.aob[2],this.aob[1],this.prp[0],this.prp[1],this.prp[2],"a2","vBottom",this.prp[5]);
								showK2('offdiv','mtopics',-110,this.prp[1],"","a2","vBottom","");
								showK2('offdivT','mtopics',-110,this.prp[1],"","a2","vTop","");
								showK2('offdivL','mtopics',0,this.prp[1],"","a2","vMiddle","hLeft");
								showK2('offdivR','mtopics',0,this.prp[1],"","a2","vMiddle","hRight");
							};
							an[i].onfocus = function(){
								if(!is.op){
									showK2(this.aob[2],this.aob[1],this.prp[0],this.prp[1],this.prp[2],"a2","vBottom",this.prp[5]);
								}
							};
							an[i].onclick = function(){
								if(window.s_account && this.getAttribute('title')){
									s_linkType='o';
									s_linkName='Masthead Menu: '+this.getAttribute('title');
									s_prop15=s_pageName;
									s_prop16=this.getAttribute('title');
									s_lnk=s_co(this);s_gs(s_account);
								}
							};
					}else if (an[i].aob[0].indexOf("k2over") > -1){
						an[i].onmouseover = function(){showK2(this.aob[2],this.aob[1],this.prp[0],this.prp[1],this.prp[2],this.prp[3],this.prp[4],this.prp[5])};
						kpop.onmouseover = function(){showK2(this.kp_objs)};
						kpop.onmouseout = function(){hideK2(this.kp_objs)};
						an[i].onmouseout = function(){hideK2(this.aob[2])};
					}else if (an[i].aob[0] == "k2click"){
						an[i].onclick = function(){
							showK2(this.aob[2],this.aob[1],this.prp[0],this.prp[1],this.prp[2],this.prp[3],this.prp[4],this.prp[5]);
							addK2(this.aob[2],this.aob[1],this.prp[0],this.prp[1],this.prp[2],this.prp[3],this.prp[4],this.prp[5]);
							return false;
						};
					}else if (an[i].aob[0] == "k2close"){
						an[i].onclick = function(){hideK2(this.aob[1],1);return false};
					}
					an[i].className = an[i].className.replace(/(k2over)-|(k2cl...)-/,"$1 ");
				}else if (an[i].className.indexOf("tickeritem") > -1 && window.s_account){
					an[i].omni = an[i].innerHTML;
					an[i].onclick = function(){
						s_linkType='o';
						s_linkName='ticker';
						s_prop15=s_pageName;
						s_prop16=this.omni;
						s_lnk=s_co(this);
						s_gs(s_account);
					};
				}
			}
		}
	}
}

//OPEN K2
function showK2(popupID,callerID,Xoffset,Yoffset,Zindex,posy,btmup,ort){
	if (is.docom){
			if (popupID.indexOf('flymenu') > -1){
				var oam = popupID;
				oam = (oam.replace(/flymenu(\d)/,"$1") * 1);
				hideA2(oam);
				clrtopic(oam,true);
			}
			var popupObj = document.getElementById(popupID);
			if (!popupObj){
				var popupObj = popupID;
			}			
			if(popupID.indexOf('flymenu') > -1 && popupObj.offsetTop > 0 && is.op || popupID.indexOf('offdiv') > -1 && popupObj.offsetTop > 0 && is.op ){
				callerID = null;
			}
			if (callerID){
				var ptop = plft = 0;
				var callerObj = document.getElementById(callerID);
				if (!callerObj){
					var callerObj = callerID;
				}			
				if (ort == "hLeft"){
					plft = plft - popupObj.offsetWidth;
				}else if (ort == "hMiddleLeft"){
					plft = plft - popupObj.offsetWidth;
					plft = plft + parseInt(callerObj.offsetWidth / 2);
				}else if (ort == "hMiddle"){
					plft = parseInt(callerObj.offsetWidth / 2);
					plft = plft - parseInt(popupObj.offsetWidth / 2);
				}else if (ort == "hMiddleRight"){
					plft = parseInt(callerObj.offsetWidth / 2);
				}else if (ort == "hRight"){
					plft = callerObj.offsetWidth;
				}
				if (btmup == "vTop"){
					ptop = ptop - popupObj.offsetHeight;
				}else if (btmup == "vMiddle"){
					ptop = ptop + parseInt(callerObj.offsetHeight / 2);
					ptop = ptop - parseInt(popupObj.offsetHeight / 2);
				}else if (btmup == "vBottom"){
					ptop = ptop + callerObj.offsetHeight;
				}
				if (is.op && posy != "a2" || is.safari && posy == "Absolute"){
					if (posy != "Absolute" && posy != "a2"){
						callerObj.style.position = "relative";
					}
					while(callerObj.offsetParent){
						plft = plft + callerObj.offsetLeft;
						ptop = ptop + callerObj.offsetTop;
						callerObj = callerObj.offsetParent;
					}
				}else{
					while(callerObj){
						plft = plft + callerObj.offsetLeft;
						ptop = ptop + callerObj.offsetTop;
						callerObj = callerObj.offsetParent;
					}
				}
				plft = plft + Xoffset;
				ptop = ptop + Yoffset;
				popupObj.style.top=ptop+'px';
				popupObj.style.left=plft+'px';
			}
			if (Zindex){
				popupObj.style.zIndex = Zindex;
			}
			popupObj.style.visibility = "visible";
	}
}

//CLOSE K2
ked = new Array();
function hideK2(popupID,popcls){
	var popupObj = document.getElementById(popupID);
	if (!popupObj){
		var popupObj = popupID;
	}			

	popupObj.style.visibility = "hidden";

	if (popcls){
		ked[popupID] = "";
	}
}

//HIDE A2 MENUS 
function hideA2(nwf){
	var fa = 1;
	while (typeof flym != "undefined" && flym[fa]){
		if(fa != nwf){
			flym[fa].style.visibility = "hidden";
		}
		clrtopic(fa, null);
		fa++;
	}
	if(nwf == 0){
		flym[0].style.visibility = "hidden";
		flym[1000].style.visibility = "hidden";
		flym[1001].style.visibility = "hidden";
		flym[1002].style.visibility = "hidden";
	}
	clrmenu(null);
}

//ADD OPENED K2s 
function addK2(p0,p1,p2,p3,p4,p5,p6,p7){
	if (is.docom){
		ked[p0] = new Array(p0,p1,p2,p3,p4,p5,p6,p7);	
	}
}

//MOVE OPENED
window.onresize = function moveK2(){
	for (kdp in ked){
		if (ked[kdp][0]){
			if (is.iemac){
				hideK2(ked[kdp][0]);
			}else{
				showK2(ked[kdp][0],ked[kdp][1],ked[kdp][2],ked[kdp][3],ked[kdp][4],ked[kdp][5],ked[kdp][6],ked[kdp][7]);
			}
		}
	}
	if(typeof flym != "undefined" && flym[1] && is.op){
		var fa = 0;
		while (flym[fa]){
			flym[fa].style.top = "-1500px";
			fa++;
		}
		flym[1000].style.top = "-1500px";
		flym[1001].style.top = "-1500px";
		flym[1002].style.top = "-1500px";
	}
}

// ADD PREPSUNPAGE ONLOAD
if (is.docom){
	if (window.attachEvent){
		window.attachEvent('onload',prepSunPage);
	}else if (window.addEventListener){
		window.addEventListener('load',prepSunPage,false);
	}else if (is.iemac){
		document.onreadystatechange = function(){if (document.readyState == "interactive"){prepSunPage()}};
	}
}

// ADD ONRESIZE EVENTS
function addOnresizeEvent(func) {
  var oldrsize = window.onresize;
  if (typeof window.onresize != 'function') {
    window.onresize = func;
  } else {
    window.onresize = function() {
      oldrsize();
      func();
    }
  }
}

// ADD CLASSES TO OBJECTS
function addClassName(element, className) {
	if (hasClassName(element, className)) { return false; }
	if (!element.className) { element.className = className; }
	else { element.className += ' '+className; }
	return true;
}
function hasClassName(element, className) {
	var exp = new RegExp("\\b"+className+"\\b");
	return (element.className && exp.exec(element.className))?true:false;
}
