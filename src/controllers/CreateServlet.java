package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.catalina.User

import models.Comment;
import models.User;
import models.validators.CommentValidator;
import utils.DBUtil;
import java.util.List;


/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");

        System.out.println(_token);



        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Comment c = new Comment();


            String title = request.getParameter("title");
            c.setTitle(title);

            String content = request.getParameter("content");
            c.setContent(content);




            c.setUser((User)request.getSession().getAttribute("login_user"));


            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            c.setCreated_at(currentTime);
            c.setUpdated_at(currentTime);

            // バリデーションを実行してエラーがあったら新規登録のフォームに戻る
            List<String> errors = CommentValidator.validate(c);
            if(errors.size() > 0) {
                em.close();

                // フォームに初期値を設定、さらにエラーメッセージを送る
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("comment", c);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/comments/new.jsp");
                rd.forward(request, response);
            } else {
                // データベースに保存
                em.getTransaction().begin();
                em.persist(c);
//
                em.getTransaction().commit();
                request.getSession().setAttribute("flush", "投稿が完了しました。");
                em.close();

                // indexのページにリダイレクト
                response.sendRedirect(request.getContextPath() + "/index");


            }


        }

    }

}

