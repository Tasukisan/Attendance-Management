package struct;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "attendance")
public class Attendance
{
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private User student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;

	@Column(name = "status")
	private AttendanceStatus status;

	@Column(name = "classtime")
	private LocalDateTime classtime;

	public Attendance()
	{
		super();
	}

	public Attendance(User student, Course course, AttendanceStatus status, LocalDateTime classtime)
	{
		super();
		this.student = student;
		this.course = course;
		this.status = status;
		this.classtime = classtime;
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the student
	 */
	public User getStudent()
	{
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(User student)
	{
		this.student = student;
	}

	/**
	 * @return the course
	 */
	public Course getCourse()
	{
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course)
	{
		this.course = course;
	}

	/**
	 * @return the status
	 */
	public AttendanceStatus getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(AttendanceStatus status)
	{
		this.status = status;
	}

	/**
	 * @return the classtime
	 */
	public java.time.LocalDateTime getClasstime()
	{
		return classtime;
	}

	/**
	 * @param classtime the classtime to set
	 */
	public void setClasstime(java.time.LocalDateTime classtime)
	{
		this.classtime = classtime;
	}

	public static LocalDateTime round(LocalDateTime time)
	{
		return time.truncatedTo(ChronoUnit.HOURS).plusMinutes(15 * (time.getMinute() / 15));
	}

}
