package struct;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "courses")
public class Course implements Comparable<Course> {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name = "course_code", unique = true, nullable = false)
	private String courseCode;

	@Column(name = "title")
	private String title;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "instructor_id")
	private User instructor;

	@Column(name = "description")
	private String description;

	@Column(name = "daysInSession")
	private int daysInSession;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "course_enrollment", joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
	private List<User> students;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "course_id")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Attendance> attendance;

	public Course() {
		this.title = "";
		this.setDescription("");
		this.setStudents(new LinkedList<User>());
		this.setDaysInSession(0);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param name the name to set
	 */
	public void setTitle(String name) {
		this.title = name;
	}

	/**
	 * @return the instructor
	 */
	public User getInstructor() {
		return instructor;
	}

	/**
	 * @param instructor the instructor to set
	 */
	public void setInstructor(User instructor) {
		this.instructor = instructor;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the daysInSession
	 */
	public int getDaysInSession() {
		return daysInSession;
	}

	/**
	 * @param daysInSession the daysInSession to set
	 */
	public void setDaysInSession(int daysInSession) {
		this.daysInSession = daysInSession;
	}

	/**
	 * @return the students
	 */
	public List<User> getStudents() {
		return students;
	}

	/**
	 * @param students the students to set
	 */
	public void setStudents(List<User> students) {
		this.students = students;
	}

	/**
	 * @return the code
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * @param code the code to set
	 */
	public void setCourseCode(String code) {
		this.courseCode = code;
	}

	public List<Attendance> getAttendance() {
		return this.attendance;
	}

	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(this.getClass())) {
			return false;
		} else {
			return this.courseCode.equals(((Course) obj).getCourseCode());
		}
	}

	public Map<AttendanceStatus, Double> getOverallAttendance() {
		Map<AttendanceStatus, Double> retVal = new HashMap<AttendanceStatus, Double>();
		int numPresent = 0;
		int numAbsent = 0;
		int numExcused = 0;

		Iterator<Attendance> it = this.attendance.iterator();
		while (it.hasNext()) {
			Attendance thisAttendance = it.next();
			switch (thisAttendance.getStatus()) {
			case PRESENT:
				numPresent += 1;
				break;
			case ABSENT:
				numAbsent += 1;
				break;
			case EXCUSED:
				numExcused += 1;
				break;
			}
		}

		if (numPresent != 0) {
			retVal.put(AttendanceStatus.PRESENT, Double.valueOf(((numPresent * 1.0) / this.attendance.size()) * 100));
		} else {
			retVal.put(AttendanceStatus.PRESENT, 0.0);
		}

		if (numAbsent != 0) {
			retVal.put(AttendanceStatus.ABSENT, Double.valueOf(((numAbsent * 1.0) / this.attendance.size()) * 100));
		} else {
			retVal.put(AttendanceStatus.ABSENT, 0.0);
		}

		if (numExcused != 0) {
			retVal.put(AttendanceStatus.EXCUSED, Double.valueOf(((numExcused * 1.0) / this.attendance.size() * 100)));
		} else {
			retVal.put(AttendanceStatus.EXCUSED, 0.0);
		}
		return retVal;
	}

	public String getStat(String statIn) {
		AttendanceStatus stat;
		if (statIn.equalsIgnoreCase("present")) {
			stat = AttendanceStatus.PRESENT;
		} else if (statIn.equalsIgnoreCase("absent")) {
			stat = AttendanceStatus.ABSENT;
		} else {
			stat = AttendanceStatus.EXCUSED;
		}

		return String.format("%.2f", getOverallAttendance().get(stat)) + "%";
	}

	@Override
	public int compareTo(Course that) {
		return this.getCourseCode().compareTo(that.getCourseCode());
	}

}
