package cn.djr.pojo.Vo;

import cn.djr.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class LoginUser implements UserDetails {

    private User user;

    public LoginUser(User user) {
        this.user = user;
    }

    public LoginUser() {
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //返回权限列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    //返回密码
    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    //返回用户名
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    //返回用户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    //返回用户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    //返回用户密码是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    //返回用户是否可用
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
