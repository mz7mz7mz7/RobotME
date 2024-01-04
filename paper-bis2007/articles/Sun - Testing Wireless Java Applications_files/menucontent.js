// MASTHEAD MENUS CONTENT VARIABLES - v.1.1
// COPYRIGHT SUN MICROSYSTEMS INC.
// QUESTIONS? webdesign -at- sun.com

// DEVELOPERS.SUN.COM link set v2.2

// translation for See All link at the end of each menu
seeall = "See All";

// set this to the location of your local css & im directories
cssdir = "/css";
imdir = "/im";

// sub menu width
mwidth = 150;

// array for all masthead menus
var navmenu = new Array();

// menus are organized using a two number decimel delineated system (1.2)
// the first number indicates which main topic link the menu belongs to.
// the second number indicates the order the sub topic link appears in the menu.

// the values for each item are then organized by a | delineated system (Link Name|URL)
// item X.0 MUST alway be the exact name of the main topic link as hardcoded in
// the masthead and must include a link that is the also the same.

// if the first main topic link was Products and it's URL was /products/ then then you
// would start the products menu with... 
//
// navmenu['1.0'] = 'Products|/products/';

// if the main topic link is not a link to another page, but simply the title of your
// menu (i.e. Select A Topic) then you would set the [X.0] item to "|". this way the
// See All Item is not created at the bottom of the menu. like...
//
// navmenu['1.0'] = '|';

navmenu['1.0'] = 'APIs|http://java.sun.com/reference/api/';
navmenu['1.1'] = 'Java SE|http://java.sun.com/reference/api/';
navmenu['1.2'] = 'Java EE|http://java.sun.com/javaee/reference/';
navmenu['1.3'] = 'Java ME|http://java.sun.com/javame/reference/apis.jsp';
navmenu['1.4'] = 'Java Card|http://java.sun.com/products/javacard/reference/docs/';
navmenu['1.5'] = 'Web Services|http://java.sun.com/webservices/reference/api/';
navmenu['1.6'] = 'Solaris|http://developers.sun.com/solaris/reference/docs/';
navmenu['1.7'] = 'Sun Studio Compilers|http://developers.sun.com/sunstudio/reference/docs/';

navmenu['2.0'] = 'Downloads|http://developers.sun.com/resources/downloads.html';
navmenu['2.1'] = 'Java SE|http://java.sun.com/javase/downloads/';
navmenu['2.2'] = 'Java EE|http://java.sun.com/javaee/downloads/';
navmenu['2.3'] = 'Java ME|http://java.sun.com/javame/downloads/';
navmenu['2.4'] = 'Solaris|http://developers.sun.com/solaris/downloads/';
navmenu['2.5'] = 'NetBeans|http://www.netbeans.org/downloads/index.html';
navmenu['2.6'] = 'Java Studio Enterprise|http://developers.sun.com/prodtech/javatools/jsenterprise/downloads/';
navmenu['2.7'] = 'Java Studio Creator|http://developers.sun.com/prodtech/javatools/jscreator/downloads/';
navmenu['2.8'] = 'Sun Studio Compilers|http://developers.sun.com/sunstudio/downloads/';
navmenu['2.9'] = 'Early Access|http://java.sun.com/downloads/ea/';

navmenu['3.0'] = 'Technologies|http://developers.sun.com/global/mh/tech/index.html';
navmenu['3.1'] = 'Java SE|http://java.sun.com/javase/';
navmenu['3.2'] = 'Java EE|http://java.sun.com/javaee/';
navmenu['3.3'] = 'Java ME|http://java.sun.com/javame/';
navmenu['3.4'] = 'Solaris|http://developers.sun.com/solaris/';
navmenu['3.5'] = 'Mobility|http://developers.sun.com/techtopics/mobility/';

navmenu['4.0'] = 'Products|http://developers.sun.com/global/mh/products/index.html';
navmenu['4.1'] = 'Java Studio Creator|http://developers.sun.com/prodtech/javatools/jscreator/';
navmenu['4.2'] = 'Java Studio Enterprise|http://developers.sun.com/prodtech/javatools/jsenterprise/';
navmenu['4.3'] = 'NetBeans Mobility Packs|http://developers.sun.com/prodtech/javatools/mobility/';
navmenu['4.4'] = 'Sun Studio Compilers|http://developers.sun.com/sunstudio/';
navmenu['4.5'] = 'Java Enterprise Systems|http://developers.sun.com/prodtech/devtools/javaes/';

navmenu['5.0'] = 'Support|http://developers.sun.com/services/';
navmenu['5.1'] = 'Forums|http://forum.java.sun.com/index.jspa';
navmenu['5.2'] = 'Developer Services|http://developers.sun.com/services/';
navmenu['5.3'] = 'Big Admin|http://www.sun.com/bigadmin/home/';

navmenu['6.0'] = 'Training|http://www.sun.com/training/';
navmenu['6.1'] = 'Certification|http://www.sun.com/training/certification/index.xml';
navmenu['6.2'] = 'Developer Training|http://www.sun.com/training/catalog/developer.xml';

navmenu['7.0'] = 'Sun.com|http://www.sun.com/';
navmenu['7.1'] = 'Products|http://www.sun.com/products/';
navmenu['7.2'] = 'Downloads|http://www.sun.com/download/';
navmenu['7.3'] = 'Services &amp; Solutions|http://www.sun.com/servicessolutions/';
navmenu['7.4'] = 'Support|http://sunsolve.sun.com/pub-cgi/show.pl?target=tous';
navmenu['7.5'] = 'Training|http://www.sun.com/training/';
navmenu['7.6'] = 'About Sun|http://www.sun.com/aboutsun/';
