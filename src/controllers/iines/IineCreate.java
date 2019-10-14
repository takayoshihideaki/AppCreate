package controllers.iines;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import models.User;
import models.Comment;
import models.Iine;


import utils.DBUtil;

/**
 * Servlet implementation class IineCreate
 */
@WebServlet("/IineCreate")
public class IineCreate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IineCreate() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        EntityManagerの生成
        EntityManager em = DBUtil.createEntityManager();

//        EntityTransactioの生成
        EntityTransaction tx = em.getTransaction();

//       EntityTransactioの開始
        tx.begin();


//      コメントid,ユーザーidを元にインスタンスを生成
        Comment c = em.find(Comment.class,Integer.parseInt(request.getParameter("comment_id")));
        User u = em.find(User.class,Integer.parseInt(request.getParameter("user_id")));

//      いいねインスタンスを生成
        Iine ii = new Iine();

//      生成したコメントid,ユーザーidをデータベースに保存
        ii.setComment(c);
        ii.setUser(u);

//      更新したデータベースを保存
        em.persist(ii);

//        EntityTransactioのデータをコッミットして終了
        tx.commit();

//      フラッシュをセッションスコープに保存
        request.getSession().setAttribute("iineflush", "いいね！を押しました");

//      /indexへフォワード
        RequestDispatcher rd = request.getRequestDispatcher("/index");
        rd.forward(request, response);
    }

}
