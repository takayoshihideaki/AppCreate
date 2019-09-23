package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import models.validators.CommentValidator;
import utils.DBUtil;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            // セッションスコープからメッセージのIDを取得して
            // 該当のIDのメッセージ1件のみをデータベースから取得
            Comment c = em.find(Comment.class, (Integer)(request.getSession().getAttribute("comment_id")));

            // フォームの内容を各フィールドに上書き
            String title = request.getParameter("title");
            c.setTitle(title);

            String content = request.getParameter("content");
            c.setContent(content);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            c.setUpdated_at(currentTime);
            // 更新日時のみ上書き

         // バリデーションを実行してエラーがあったら新規登録のフォームに戻る
            List<String> errors = CommentValidator.validate(c);
            if(errors.size() > 0) {
                em.close();

                // フォームに初期値を設定、さらにエラーメッセージを送る
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("comment", c);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/commens/new.jsp");
                rd.forward(request, response);
            } else {
                // データベースに保存


            // データベースを更新
            em.getTransaction().begin();
            em.getTransaction().commit();
            request.getSession().setAttribute("flush", "更新が完了しました。");
            em.close();

            // セッションスコープ上の不要になったデータを削除
            request.getSession().removeAttribute("comment_id");

            // indexページへリダイレクト
            response.sendRedirect(request.getContextPath() + "/index");
        }
        }
    }
}
