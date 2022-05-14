package com.example.myapplication.screens.core.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Spot

class HomeViewModel: ViewModel() {
    val spotArray = MutableLiveData<ArrayList<Spot>>().apply {
        val spots = ArrayList<Spot>()
        spots.add(Spot(name = "Чайный магазин", city = "Cтоимость бизнеса", url = "https://www.openbusiness.ru/upload/iblock/91e/chainy_magazin.jpg"))
        spots.add(Spot(name = "Автомойка", city = "Cтоимость бизнеса", url = "https://i1.photo.2gis.com/main/branch/49/70000001039960676/common"))
        spots.add(Spot(name = "Автозаправка", city = "Cтоимость бизнеса", url = "https://bisnesideya.ru/wp-content/uploads/2017/05/kak-otkrit-azs1.jpg"))
        spots.add(Spot(name = "Аренда квартир", city = "Cтоимость бизнеса", url = "https://s0.rbk.ru/v6_top_pics/media/img/7/03/754678190004037.jpeg"))
        spots.add(Spot(name = "Ремонт компьютеров", city = "Cтоимость бизнеса", url = "https://spez-servis.ru/wp-content/uploads/2016/12/66306094_remont-kompyuterov-almaty.jpg"))
        spots.add(Spot(name = "Отделение почты", city = "Cтоимость бизнеса", url = "https://retailers.ua/media/news/1100-s-crop-w/00/02/2011/unnamed-2587-2850-3099-3679-3692.jpg"))
        spots.add(Spot(name = "Разработка игр", city = "Cтоимость бизнеса", url = "https://kvantprogramm.ru/wp-content/uploads/2018/06/game2.jpg"))
        spots.add(Spot(name = "Продажа картошки", city = "Cтоимость бизнеса", url = "https://politikus.ru/uploads/posts/2020-05/1589587429_maxresdefault-10.jpg"))
        spots.add(Spot(name = "Продажа воды опром", city = "Cтоимость бизнеса", url = "https://g-kom.com/upload/iblock/712/712887224c0935ee097572e071a70d38.jpg"))
        spots.add(Spot(name = "Покупки из китая", city = "Cтоимость бизнеса", url = "https://chinaprices.ru/blog/wp-content/uploads/2015/04/1.jpg"))
        value = spots
    }



}