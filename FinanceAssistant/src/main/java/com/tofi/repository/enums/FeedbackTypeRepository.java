package com.tofi.repository.enums;

import com.tofi.model.enums.FeedbackType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ulian_000 on 26.12.2016.
 */
@Repository
public interface FeedbackTypeRepository extends JpaRepository<FeedbackType, Long>, EnumRepository<FeedbackType> {

}
