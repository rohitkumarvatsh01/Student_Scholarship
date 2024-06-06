There is requirement to design and implement the Microservice based API for student service. This API is to decide the student eligibility for scholarship  based on their marks. Following are the requirements to consider for this API –

Number of records in csv file would range between 0 to 50k.
Swagger end point to upload the csv file and kick off the processing. Any user can upload and process multiple files.
Csv structure – roll number, student name, science, maths. English, computer, Eligible for e.g. 100101, Vivek Sharma, 86, 89, 78, 92, ToBeChecked
Output of this API would be a same csv file with last column updated as YES/NO.
Scholarship eligibility criteria is dynamic and that could change before processing any file. For e.g. if science marks > 85, maths marks > 90, computer marks > 95 and English marks > 75 then student is eligible. There should be flexibility to update this criteria.
Any point of time, I should be able to search the student using roll number via swagger end point and it should return the status of eligibility or NA if data is not present for that student. In memory DB/cache could be used for this requirement.
To speed up the csv file processing, multiple threads can be used for processing.
 


While writing code take care of following points.
1. Code should be committed in GitHub.
2. Appropriate logging should be there.
3. Unit test cases should be there.
4. Swagger documentation should there.
