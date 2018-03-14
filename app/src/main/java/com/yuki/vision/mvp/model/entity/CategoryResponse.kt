package com.yuki.vision.mvp.model.entity

import java.io.Serializable

/**
 * 项目：Vision
 * 作者：Yuki - 2018/1/31
 * 邮箱：125508663@qq.com
 **/


data class CategoryResponse(val id: Long, val name: String,
                            val description: String, val bgPicture: String, val bgColor: String, val headerImage: String) : Serializable
