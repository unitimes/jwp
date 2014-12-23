2014년 개발 경험 프로젝트
=========

1. 로컬 개발 환경에 Tomcat 서버를 시작한 후 http://localhost:8080으로 접근하면 질문 목록을 확인할 수 있다. http://localhost:8080으로 접근해서 질문 목록이 보이기까지의 소스 코드의 호출 순서 및 흐름을 설명하라.

* '/'로 접근 시 web.xml의 welcom-file-list 설정에 의해 /index.jsp 호출
* index.jsp의 response.sendRedirect("/list.next")호출
* annotation servlet mapping에 의해 *.next 형식의 url접근을 FrontController 서블릿으로 매핑
* FrontController 서블릿 최초 실행(http request)
	* 서블릿 최초 실행 시 서블릿이 메모리에 온로드 되며 이는 servlet context에 변화를 줌
	* servlet context변화는 annotation에 의해 WebListener로 등록 한ServletContextLoader의 contextInitialized 호출
		* ServeletContext sc 생성
		* RequestMapping rm 생성
		* rm.initMapping() 호출
			* mappings 변수에 url을 key로, Controller를 value로 하여 저장
		* sc attribute에 DEFAULT_REQUEST_MAPPING을 이름으로 하여 rm object setting
	* inti() 호출 
		* rm에 sc의 attribute에 세팅했던 rm 할당
	* servie() 호출 
		* request uri를 key로 하여 rm에서 controller를 받아와 controller 변수에 할당
			* ListController가 할당 됨
		* ListController의 execute() 호출하여 return값을 view에 할당
			* questions 변수에 DB의 question list 할당
			* JstlView.viewName을 list.jsp로 하는 view를 가지는 ModelAndView object를 mav에 할당
			* mav에 questions를 속성이름으로하여 questions object를 model에 추가
			* return mav
		* mav로 부터 view를 받아와 지역변수 view에 할당
		* view.render()호출
			* viewName이 "redirect:"로 시작하지 않음
			* mav의 model을 받아와 request에 attributes로 세팅
			* list.jsp(viewName에 할당된 url)로 request, response 포워드
* list.jsp에서 jstl과 expression language를 통해 request에 속성값(questions)을 불러와 html부분 완성 후 html변환 후 response. 