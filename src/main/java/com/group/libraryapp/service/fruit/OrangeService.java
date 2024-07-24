package com.group.libraryapp.service.fruit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
//@Primary 동시에 사용시 사용하는 쪽에 직접 적용한 @Qualifier를 우선 적용 사용자가 명시한 것을 보편적으로 스프링은 먼저 적용 해준다.
public class OrangeService implements FruitService {
}
