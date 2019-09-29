package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import models.Comment;

import models.User;
import utils.DBUtil;
import javax.servlet.RequestDispatcher;



/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        User u = (User) request.getSession().getAttribute("login_user");




     // 開くページ数を取得（デフォルトは1ページ目）
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) {}

        // 最大件数と開始位置を指定してメッセージを取得
        List<Comment> comments = em.createNamedQuery("getAllComments", Comment.class)
                                   .setFirstResult(15 * (page - 1))
                                   .setMaxResults(15)
                                   .getResultList();

        // 全件数を取得
        long comments_count = (long)em.createNamedQuery("getCommentsCount", Long.class)
                                      .getSingleResult();

        List<Integer> iineCountList = new ArrayList<Integer>();

        for(Comment comment : comments) {
            long iineCount = (long)em.createNamedQuery("getIineCount", Long.class)
                    .setParameter("id", comment.getId())
                    .getSingleResult();
            iineCountList.add((int)iineCount);
        }

        List<Integer> iineCountList2 = new ArrayList<Integer>();

        for(Comment comment : comments) {
            long user_iine = (long)em.createNamedQuery("getIineCount2", Long.class)
                    .setParameter("id",comment.getId())
                    .setParameter("idd",u.getId())
                    .getSingleResult();
            iineCountList2.add((int)user_iine);
        }






        em.close();




        request.setAttribute("iineCountList",iineCountList );
        request.setAttribute("iineCountList2",iineCountList2 );
        request.setAttribute("comments", comments);
        request.setAttribute("comments_count", comments_count);     // 全件数
        request.setAttribute("page", page);                         // ページ数

     // フラッシュメッセージがセッションスコープにセットされていたら
        // リクエストスコープに保存する（セッションスコープからは削除）
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/comments/index.jsp");
        rd.forward(request, response);


}

}