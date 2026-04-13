package com.example.practice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
@Configuration
public class StudentConfig {
 
	@Bean(name="st1")
	public  Student getstudent()
	{
		Student s = new Student();
		
		s.setRollno(101);
		s.setName("Ajay");
		s.setMarks(90);
				
		return s;
		
		
	}
	
	
	
	
	@Bean(name="clg1")
	public  Collage getcollage()
	{
		Collage c = new Collage();
		
		c.setC_id(101);
		c.setC_name("ABC");
		
				
		return c;
		
		
	}
	
	
}