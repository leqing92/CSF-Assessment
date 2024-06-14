package ibf2023.csf.backend.repositories;

import java.util.Calendar;
import java.util.Date;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import ibf2023.csf.backend.models.Post;

@Repository
public class ImageRepository {

	@Autowired
	MongoTemplate mongoTemp;

	// TODO Task 4.1
	// You may change the method signature by adding parameters and/or the return type
	// You may throw any exception 

	/*
	 db.travelpics.insert([
	 "_id" :
	 "date" : 
	 "title" :
	 "comments" :
	 "url" :
	 ])
	*/ 
	public void save(Post post) {
		// IMPORTANT: Write the native mongo query in the comments above this method
		mongoTemp.save(post, "travelpics");
	}

	// https://www.mongodb.com/docs/manual/reference/operator/aggregation/year/
	/*
	 db.travelpics.aggregate([
		{
		$project : {
			year : { $year : "$date"},
			month: { $month: "$date" },
			fileSize : 1
		}
		},
		{
			$match : {
				year : 2024,
				month : 6
			}
		},
		{
			$group : {
				_id : "total",
				total : {$sum :"$fileSize"}
			}
		}
		
	])
	 */
	public long getFileSize(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH ) + 1;
		// System.out.println(calendar.get(Calendar.MONTH) + 1);
		// System.out.println(calendar.get(Calendar.YEAR));
		
		// https://stackoverflow.com/questions/69194154/use-dateoperators-month-withtimezone-correctly-in-a-spring-data-mongo-aggregatio
		ProjectionOperation projOps = Aggregation.project()
											.and(DateOperators.Year.year("$date")).as("year")
											.and(DateOperators.Month.monthOf("$date")).as("month")
											.and("fileSize").as("fileSize");		
	
		MatchOperation matchOps = Aggregation.match(Criteria.where("year").is(year)
															.and("month").is(month));		 														

		GroupOperation groupOps = Aggregation.group()
											.sum("fileSize").as("total");

 		Aggregation pipeline = Aggregation.newAggregation(projOps, matchOps, groupOps);

 		AggregationResults<Document> results = mongoTemp.aggregate(pipeline, "travelpics", Document.class);

		// for (Document doc : results) {
		// 	System.out.println("\n\n\n\n");
		// 	System.out.println(doc.toJson());
		// }		
		
		if(!results.getMappedResults().isEmpty()){
			return results.getMappedResults().get(0).getLong("total");		
		}
		return 0;
	}     

}