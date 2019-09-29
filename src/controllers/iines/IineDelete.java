package controllers.iines;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IineDelete
 */


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.RequestDispatcher;

import models.Comment;

import models.Iine;

import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class IineCreate
 */
@WebServlet("/IineDelete")
public class IineDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IineDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

         EntityTransaction tx = em.getTransaction();

        tx.begin();

        Comment c = em.find(Comment.class,Integer.parseInt(request.getParameter("comment_id")));
        User u = em.find(User.class,Integer.parseInt(request.getParameter("user_id")));

        Iine ii = em.createNamedQuery("IineDelete",Iine.class)
                .setParameter("id",c.getId())
                .setParameter("idd",u.getId())
                .getSingleResult();

     em.remove(ii);

      tx.commit();

      request.getSession().setAttribute("iineflush2", "いいね！を取り消しました");

        RequestDispatcher rd = request.getRequestDispatcher("/index");
        rd.forward(request, response);
    }

}