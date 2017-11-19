package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_comment_id_seq")
    @SequenceGenerator(sequenceName = "comment_comment_id_seq",
            name = "comment_comment_id_seq", allocationSize = 1)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OrderBy
    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
    private List<Comment> children;

    @Column(name = "comment")
    private String comment;

    Comment (){
        /* For Hibernate*/
    }

    public Comment (User creator, Date date, String comment){
        this.creator = creator;
        this.date = date;
        this.comment = comment;
        this.children = new ArrayList<>();
    }

    public Comment (User creator, Date date, String comment, Comment parent){
        this.creator = creator;
        this.date = date;
        this.comment = comment;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public Long getId(){
        return id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void addChildren(Comment child){
        if (!this.children.contains(child)){
            this.children.add(child);
        }
    }

    public List<Comment> getChildren(){
        return this.children;
    }

    public void setChildren(List<Comment> children){
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return id.equals(comment.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", creator=" + creator +
                ", date=" + date +
                '}';
    }
}
