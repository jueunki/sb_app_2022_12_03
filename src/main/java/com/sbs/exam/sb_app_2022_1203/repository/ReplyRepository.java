package com.sbs.exam.sb_app_2022_1203.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReplyRepository {

  @Insert("""
          INSERT INTO reply
          SET regDate = NOW(),
          updateDate = NOW(),
          memberId = #{memberId},
          relTypeCode = #{relTypeCode},
          relId = #{relId},
          body = #{body}
          """)
  void writeArticle(@Param("memberId")int memberId,
                    @Param("relTypeCode")String relTypeCode,
                    @Param("relId")int relId,
                    @Param("body")String body);

  @Select("""
          SELECT LAST_INSERT_ID()
          """)
  int getLastInsertId();
}
