package core.sys.util

import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

import java.io.IOException
import java.net.URI

object HttpUtil {

    @JvmOverloads
    fun doGet(url: String, param: Map<String, String>? = null): String {
        // 创建Httpclient对象
        val httpclient = HttpClients.createDefault()
        var resultString = ""
        var response: CloseableHttpResponse? = null
        try {
            // 创建uri
            val builder = URIBuilder(url)
            if (param != null) {
                for (key in param.keySet()) {
                    builder.addParameter(key, param[key])
                }
            }
            val uri = builder.build()
            // 创建http GET请求
            val httpGet = HttpGet(uri)
            httpGet.addHeader("accept", "*/*")
            httpGet.addHeader("accept-encoding", "gzip, deflate, br")
            httpGet.addHeader("accept-language", "zh-CN,zh;q=0.9")
            httpGet.addHeader("cookie", "_xsrf=HelGHU20p0a41c8Vf7TGrGDFJmpaFSin; _zap=9a69de01-d902-4eda-8bba-7e204b002e08; d_c0=\"ANBg4TsUYRCPTk-yDDr7F4KSsfKue4j3QDM=|1574172770\"; _ga=GA1.2.1734212343.1587469488; _gid=GA1.2.982807076.1587469488; r_cap_id=\"2|1:0|10:1587469555|8:r_cap_id|44:NGQ0MDQ3ZGQzNGMyNDc1NmExYTA4Nzc0MmIxM2VmOTU=|fd7ff5fd4938a8c3e8ba3606e3ef517c81e668a9ae027cdb6d2e7e5c5b9ff015\"; SESSIONID=AJ1paYCujlCnBcQ42BrqTs2mmwoXxvmO9CqJYGEPQwG; JOID=U1sQA02eo9lDE1Y6FJ-GBhxN1xcB3e_tNXUNf2TzzZwoVzJpJl6NehwWUjkX2AhuSpw0uq-lPVfybUHsF7IgW6w=; osd=V1ASBU6aqNtFEFIxFpmFAhdP0RQF1u3rNnEGfWLwyZcqUTFtLVyLeRgdUD8U3ANsTJ8wsa2jPlP5b0fvE7kiXa8=; Hm_lvt_98beee57fd2ef70ccdd5ca52b9740c49=1587555750,1587555771; _gat_gtag_UA_149949619_1=1; capsion_ticket=\"2|1:0|10:1587555934|14:capsion_ticket|44:MmVlNTQxNDg0NWFlNGU3ODlhYzQwNDg2ZWE1NmE0NDM=|3eee07f36e752871d91a63521f5ceb2296006d09d80c9c359a23e1117429525d\"; KLBRSID=d017ffedd50a8c265f0e648afe355952|1587555942|1587555776; Hm_lpvt_98beee57fd2ef70ccdd5ca52b9740c49=1587555913")
            httpGet.addHeader("referer", " https://www.zhihu.com/search?q=%E8%A5%BF%E5%AE%89%E7%90%86%E5%B7%A5%E5%A4%A7%E5%AD%A6&utm_content=search_history&type=content")
            httpGet.addHeader("sec-fetch-mode", "cors")
            httpGet.addHeader("sec-fetch-site", "same-origin")
            httpGet.addHeader("x-ab-param", "pf_profile2_tab=0;se_cardrank_2=1;se_v038=0;tp_sft_v2=d;top_hotcommerce=1;tp_club_pic=0.6;tp_topic_rec=1;li_viptab_name=0;li_ebok_chap=0;se_new_merger=1;se_cbert_index=1;pf_noti_entry_num=0;li_yxzl_new_style_a=1;zw_sameq_sorce=999;tp_header_style=1;se_club_boost=0;tp_movie_ux=0;qap_labeltype=1;qap_question_visitor= 0;soc_zuichangfangwen=0;pf_fuceng=1;ug_zero_follow_0=0;tp_club_tab_feed=0;soc_brdcst4=3;tp_score_1=a;li_se_section=1;qap_question_author=0;se_pek_test3=1;tp_club_pic_swiper=1;tp_qa_toast=1;tp_qa_metacard=1;soc_adweeklynew=0;top_ebook=0;li_answer_test=3;li_vip_verti_search=0;zr_answer_rec_cp=open;zr_test_aa1=0;soc_feed_intimacy=2;tsp_hotlist_ui=1;tsp_videobillboard=1;qap_thanks=1;zr_intervene=0;tp_club_pk=1;soc_adpinweight=0;soc_iosweeklynew=0;soc_adreadfilter=0;soc_leave_recommend=2;pf_adjust=0;li_answer_test_2=0;se_rel_bi=0;tp_club_qa_pic=1;soc_ioshotrenew=0;ls_recommend_test=0;zr_rec_answer_cp=close;zr_slotpaidexp=1;tp_sft=a;tp_club_flow_ai=0;tp_m_intro_re_topic=1;soc_zcfw_broadcast2=1;li_salt_hot=1;zr_search_topic=0;zr_rel_search=base;se_specialbutton=0;top_universalebook=1;top_test_4_liguangyi=1;li_hot_voted=0;tp_sticky_android=2;tp_move_scorecard=0;ug_goodcomment_0=1;se_highlight_new=0;pf_newguide_vertical=0;tp_club_feed=1;soc_authormore=2;top_ydyq=X;li_paid_answer_exp=0;tp_topic_entry=0;tp_discover_copy=0;tp_club_android_join=1;ls_videoad=2;zr_search_paid=0;se_colorfultab=1;se_v035=0;tp_discovery_ab_1=0;li_topics_search=0;se_searchwiki=0;se_sug_term=0;tp_qa_metacard_top=top;soc_yxzl_zcfw=0;soc_zcfw_shipinshiti=1;top_v_album=1;zr_art_rec=base;se_pek_test=1;se_backsearch=0;pf_creator_card=1;tp_club_header=1;zr_expslotpaid=1;se_col_boost=0;se_clarify=0;tp_club_android_feed=old;top_quality=0;ug_zero_follow=0;li_catalog_card=1;se_pek_test2=1;se_whitelist=0;tp_club_join=0;zr_slot_training=1;soc_notification=1;ug_fw_answ_aut_1=0;soc_userrec=2;soc_adreadline=0;top_new_feed=5;ug_follow_answerer=0;qap_article_like=1;se_hotsearch_num=1;tp_topic_tab=0;tp_discover=0;ug_follow_topic_1=2;zr_search_sim=0;zr_article_new=close;se_expired_ob=0;se_relation_1=2;se_hotsearch_2=1;ug_newtag=0;soc_iosreadline=0;pf_foltopic_usernum=50;zr_slot_up2=0;se_topicfeed=0;se_college=default;soc_iosreadfilter=0;li_svip_cardshow=1;se_faxian_jiahao=0;se_hotmore=2;tp_topic_tab_new=0-0-0;soc_iospinweight=0;li_assessment_show=1;se_dnn_mt_v2=0;se_content0=1;tp_club_tab=0;li_education_box=0;zr_ans_rec=gbrank;zr_training_boost=false;se_page_quality=1;tp_meta_card=0;tp_topic_head=0;soc_zcfw_broadcast=0;soc_authormore2=2;se_club_post=5;se_searchvideo=0;se_ffzx_jushen1=0;se_billboardsearch=0;top_root=0;soc_stickypush=1;ug_follow_answerer_0=0;li_ebook_gen_search=0;se_highlight_v2=0;se_aa_base=0;se_hotsearch=1;se_cardrank_3=0;tp_topic_style=0;soc_zcfw_badcase=0;li_answers_link=0;li_video_section=0;zr_training_first=false;soc_cardheight=2;ls_fmp4=0;se_clubrank=1;se_cardrank_4=1;se_multianswer=2;tp_club_discover=0;li_svip_tab_search=0;li_answer_card=0;zr_km_answer=open_cvr;tp_club_qa=1;soc_iosintimacy=2;soc_newfeed=2;soc_wonderuser_recom=2")
            httpGet.addHeader("x-api-version", "3.0.91")
            httpGet.addHeader("x-app-za", "OS=Web")
            httpGet.addHeader("x-requested-with", "fetch")
            httpGet.addHeader("x-zse-83", "3_2.0")
            httpGet.addHeader("x-zse-86", "1.0_a8S8NhuBSXxY68S0Z920nJ906LFxS7Fqf728cHrqFwSf")
            httpGet.addHeader("user-agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
            // 执行请求
            response = httpclient.execute(httpGet)
            // 判断返回状态是否为200
            if (response!!.getStatusLine().getStatusCode() === 200) {
                resultString = EntityUtils.toString(response!!.getEntity(), "UTF-8")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (response != null) {
                    response!!.close()
                }
                httpclient.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return resultString
    }
}
