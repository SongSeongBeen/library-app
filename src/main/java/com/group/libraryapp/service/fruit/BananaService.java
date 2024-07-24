package com.group.libraryapp.service.fruit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("main") //(bean등록 시 동일한 명칭으로 맞춰서 사용가능)
public class BananaService implements FruitService {
}
