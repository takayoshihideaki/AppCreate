package models;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "iines")
@NamedQueries({

    @NamedQuery(
            name = "getIineCount",
            query = "SELECT COUNT(distinct i.user) from Iine i where i.comment.id = :id"
            ),
    @NamedQuery(
            name = "getIineCount2",
            query = "SELECT COUNT(i) from Iine i where i.user.id = :idd and i.comment.id = :id"
            ),
    @NamedQuery(
            name = "IineDelete",
            query = "SELECT i from Iine i where i.user.id = :idd and i.comment.id = :id"
            )


})


@Entity
public class Iine {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }



}