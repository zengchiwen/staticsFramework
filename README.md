# staticsFramework
 staticsFramework
	  The data statistics framework realized by AOP can realize the statistics of the interface traffic statistics 
and the number of users clicking on a page and the time of the page's stay.
	  There are the following characteristics:
	  1. access is simple, two lines of code,
	  2. annotation operation is easy to understand.
	  The methods used are as follows: first, initialize the management class in application. 
    (1) at the time of traffic statistics, the flow consumption can be obtained by annotating the method of calling the interface. 
    As follows:
	 @NetStaticsTrace ("XXX")
   Private void checkBind ()
	 (2) according to the number of pages and time for the user to click on the page, only the onresume and onpause methods can be 
 annotated to get the corresponding data.
	 @UserBehaviorTrace ("XXX")
	 Protected void onResume ()
	 @UserBehaviorTrace ("XXX")
	 Protected void onPause ()
