package life.iGuaDa.community.mapper;

import life.iGuaDa.community.dto.*;
import life.iGuaDa.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    /**
     *
     * 增加浏览量的dao层
     * @param record
     * @return
     */
    int incView(Question record);
    /**
     *
     * 更新评论的dao层
     * @param record
     * @return
     */
    int incCommentCount(Question record);
    /**
     *
     * 根据id和tag选择相关的dao层
     * @param question
     * @return
     */
    List<Question> selectRelated(Question question);
    /**
     *
     * 根据搜索来选择个数
     * @param questionQueryDTO
     * @return
     */
    Integer countBySearch(QuestionQueryDTO questionQueryDTO);
    /**
     *
     *
     * @param questionQueryDTO
     * @return
     */
    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
    /**
     *
     * 选择sticky > 0 的问题
     * @return
     */
    List<Question> selectSticky();
    /**
     *
     * 增加新的收集
     * @param addToCollectionDTO
     */
    void insertCollection(AddToCollectionDTO addToCollectionDTO);
    /**
     *
     * 删除收藏
     * @param deleteFromCollectionDTO
     */
    void deleteFromCollection(DeleteFromCollectionDTO deleteFromCollectionDTO);

    /**
     *
     * 根据user寻找问题
     * @param questionQueryUserIdDTO
     * @return
     */
    List<Question> findQuestionsByUser(QuestionQueryUserIdDTO questionQueryUserIdDTO);
    /**
     * 根据user和question来选择collect
     * @param questionQueryUserIdDTO
     * @return
     */
    Long findQuestionsCountByUser(QuestionQueryUserIdDTO questionQueryUserIdDTO);

    /**
     * 根据question和user返回关系数
     * @return
     */
    Long checkCollectionCount(CheckCollectionDTO checkCollectionDTO);
}