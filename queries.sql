--Part 1
--id: int, employer: varchar, name: varchar, skills: varchar
--Part 2
SELECT name
FROM employer
WHERE location = "St. Louis City";
--Part 3
DROP TABLE job;
--Part 4
SELECT * FROM skill
INNER JOIN job_skills
WHERE job_skills.jobs_id != NULL
ORDER BY name ASC;