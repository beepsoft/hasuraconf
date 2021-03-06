package com.beepsoft.hasuraconf.model


import com.beepsoft.hasuraconf.annotation.HasuraAlias
import com.beepsoft.hasuraconf.annotation.HasuraRootFields
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable
import javax.persistence.*

/**
 * A user.
 */
@Entity
@Table(indexes = arrayOf(
        Index(columnList = "createdAt"),
        Index(columnList = "updatedAt"),
        Index(columnList = "id"))
)
class CalendarUser : BaseObject(), Serializable {
    @Column(unique = true)
    var firebaseUserId: String? = null
    @Column(unique = true)
    var email: String? = null
    var nickname: String? = null
    var firstName: String? = null
    var lastName: String? = null
    //private  Set<AppRole> authorities;
    @Column(columnDefinition = "boolean default true", nullable = false)
    var enabled: Boolean = true
    @Column(unique = true)
    var username: String? = null
    @HasuraAlias(fieldAlias = "superSecretPassword")
    var password: String? = null

    @ManyToMany
    @HasuraAlias(
            fieldAlias="theFriends",
            joinColumnAlias="myUserId",
            inverseJoinColumnAlias= "myFriendUserId",
            joinFieldAlias="myFriend",
            rootFieldAliases = HasuraRootFields(
                    baseName = "BaseDearFriend",
                    select = "myDearFriends",
                    insert = "addMyDearFriends",
                    insertOne = "addOneDearFriend"
            )
    )
    var friends: List<CalendarUser>? = null

    @ManyToMany
    @OrderColumn(name="calendar_order", nullable = false)
    @JoinTable(name="user_calendar",
            joinColumns=arrayOf(JoinColumn(name="the_user_id")),
            inverseJoinColumns=arrayOf(JoinColumn(name="the_calendar_id")))
    @OnDelete(action=OnDeleteAction.CASCADE)
    var calendars: List<Calendar>? = null

}
