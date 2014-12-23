package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class SaveController extends AbstractController{

	@Override
	public ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Question question = new Question(ServletRequestUtils.getStringParameter(request, "writer"), ServletRequestUtils.getStringParameter(request, "title"), ServletRequestUtils.getStringParameter(request, "contents"));
		QuestionDao.getDao().insert(question);
		return new ListController().execute(request, response);
	}
}
