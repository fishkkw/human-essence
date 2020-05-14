package core.sys.common

import core.sys.util.HttpUtil

object main {
    fun main(args: Array<String>) {
        System.out.println(HttpUtil.doGet("https://www.zhihu.com/api/v4/search_v3?t=general&q=%E8%A5%BF%E5%AE%89%E7%90%86%E5%B7%A5%E5%A4%A7%E5%AD%A6&correction=1&offset=0&limit=20&lc_idx=0&show_all_topics=0"))
    }
}
