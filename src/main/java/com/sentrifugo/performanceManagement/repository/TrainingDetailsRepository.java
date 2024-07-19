

package com.sentrifugo.performanceManagement.repository;
import com.sentrifugo.performanceManagement.entity.TrainingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface TrainingDetailsRepository extends JpaRepository<TrainingDetails,Integer> {

    @Query(value = "select TrainingName,count(TrainingName) from training_details td  where td.StartDate BETWEEN :start and :end group by TrainingName",nativeQuery = true)
    List<Map<String, Integer>> getByTrainingName(LocalDate start, LocalDate end);
    @Query(value = "select TrainingArea,count(*) from training_details td  where td.StartDate BETWEEN :start and :end group by TrainingArea",nativeQuery = true)
    List<Map<String, Integer>> getByTrainingArea(LocalDate start, LocalDate end);
    @Query(value = "select sum(NumberOfTrainingHours), Count(NumberOfNominees) from training_details td  where td.StartDate BETWEEN :start and :end ",nativeQuery = true)
    List<Map<String, Integer>> getTotalTrainingHrs(LocalDate start, LocalDate end);

//    @Query(value = "SELECT SUM(CASE WHEN MONTH(td.StartDate) IN (1, 2, 3) THEN td.NumberOfTrainingHours ELSE 0 END) AS quater1,SUM(CASE WHEN MONTH(td.StartDate) IN (4, 5, 6) THEN td.NumberOfTrainingHours ELSE 0 END) AS quater2,SUM(CASE WHEN MONTH(td.StartDate) IN (7, 8, 9) THEN td.NumberOfTrainingHours ELSE 0 END) AS quater3,SUM(CASE WHEN MONTH(td.StartDate) IN (10, 11, 12) THEN td.NumberOfTrainingHours ELSE 0 END) AS quater4 FROM training_details td  WHERE YEAR(td.StartDate) =:year ", nativeQuery = true)
    @Query(value = "SELECT SUM(CASE WHEN MONTH(td.StartDate) IN (4, 5, 6) THEN td.NumberOfTrainingHours ELSE 0 END) AS quater1," +
        "SUM(CASE WHEN MONTH(td.StartDate) IN (7, 8, 9) THEN td.NumberOfTrainingHours ELSE 0 END) AS quater2," +
        "SUM(CASE WHEN MONTH(td.StartDate) IN (10, 11, 12) THEN td.NumberOfTrainingHours ELSE 0 END) AS quater3," +
        "(SELECT COUNT(*) FROM training_details WHERE MONTH(StartDate) IN (1, 2, 3) AND YEAR(StartDate) = YEAR(DATE_ADD(CONCAT(:year, '-01-01'), INTERVAL 1 YEAR))) AS quater4 FROM training_details td WHERE YEAR(td.StartDate) = :year",nativeQuery = true)
    List<Map<String, Integer>> getQuarterlyData(String year);

    @Query(value = "SELECT SUM(CASE WHEN MONTH(td.StartDate) IN (4, 5, 6) THEN td.NumberOfNominees ELSE 0 END) AS quater1," +
        "SUM(CASE WHEN MONTH(td.StartDate) IN (7, 8, 9) THEN td.NumberOfNominees ELSE 0 END) AS quater2," +
        "SUM(CASE WHEN MONTH(td.StartDate) IN (10, 11, 12) THEN td.NumberOfNominees ELSE 0 END) AS quater3," +
        "(SELECT COUNT(*) FROM training_details WHERE MONTH(StartDate) IN (1, 2, 3) AND YEAR(StartDate) = YEAR(DATE_ADD(CONCAT(:year, '-01-01'), INTERVAL 1 YEAR))) AS quater4 FROM training_details td WHERE YEAR(td.StartDate) = :year",nativeQuery = true)
    List<Map<String, Integer>> getQuarterlyNominatedData(String year);

    @Query(value = "SELECT SUM(CASE WHEN MONTH(td.StartDate) IN (4, 5, 6) THEN td.TrainingArea = 'Technical' ELSE 0 END) AS quater1," +
        "SUM(CASE WHEN MONTH(td.StartDate) IN (7, 8, 9) THEN td.TrainingArea = 'Technical' ELSE 0 END) AS quater2," +
        "SUM(CASE WHEN MONTH(td.StartDate) IN (10, 11, 12) THEN td.TrainingArea = 'Technical' ELSE 0 END) AS quater3," +
        "(SELECT COUNT(*) FROM training_details WHERE MONTH(StartDate) IN (1, 2, 3) AND YEAR(StartDate) = YEAR(DATE_ADD(CONCAT(:year, '-01-01'), INTERVAL 1 YEAR))) AS quater4 FROM training_details td WHERE YEAR(td.StartDate) = :year",nativeQuery = true)
    List<Map<String, Integer>> getQuarterlyTechAreaData(String year);
}
