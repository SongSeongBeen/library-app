package com.group.libraryapp.config;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class UserConfiguration {
    /* 직접 만들지 않고 외부 라이브러리나 프레임워크에서 만든 곳에 사용
    @Bean
    public UserRepository userRepository(JdbcTemplate jdbcTemplate) {
        return new UserRepository(jdbcTemplate);

    }
    */
}
