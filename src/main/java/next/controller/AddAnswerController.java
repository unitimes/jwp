package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class AddAnswerController extends AbstractController{

	@Override
	public ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Answer answer = new Answer(ServletRequestUtils.getStringParameter(request, "writer"), ServletRequestUtils.getStringParameter(request, "contents"), ServletRequestUtils.getLongParameter(request, "questionId"));
		AnswerDao.getDao().insert(answer);
		QuestionDao.getDao().increaseConuntOfComment(ServletRequestUtils.getIntParameter(request, "questionId"));
		ModelAndView mav = jstlView("api");
		return mav;
	}

}
