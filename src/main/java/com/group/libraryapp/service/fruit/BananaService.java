package com.group.libraryapp.service.fruit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Qualifier("main")
@Service
public class BananaService implements FruitSerivce{
}
