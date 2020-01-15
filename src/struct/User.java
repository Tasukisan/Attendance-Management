package struct;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "users")
public class User implements Comparable<User> {

	private static final Logger log = LoggerFactory.getLogger(User.class);

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;

	@Column(name = "type", nullable = false)
	private UserType type;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "course_enrollment", joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
	private List<Course> coursesEnrolled;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "instructor_id")
	private List<Course> coursesTaught;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "student_id")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Attendance> attendance;

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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Validate that a passed password matches the password for this user.
	 * 
	 * @param password The password to validate
	 * @return true IFF the password hashes correctly
	 */
	public boolean authUser(String password) {
		boolean result = this.password.equals(Encrypt.hash(password));
		if (result) {
			log.info("Successful authentication for user: {}", this.username);
		} else {
			log.info("Unsuccessful authentication for user: {}", this.username);
		}
		return result;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = Encrypt.hash(password);
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the type
	 */
	public UserType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(UserType type) {
		this.type = type;
	}

	/**
	 * @return the coursesEnrolled
	 */
	public List<Course> getCoursesEnrolled() {
		return coursesEnrolled;
	}

	/**
	 * @param coursesEnrolled the coursesEnrolled to set
	 */
	public void setCoursesEnrolled(List<Course> coursesEnrolled) {
		this.coursesEnrolled = coursesEnrolled;
	}

	/**
	 * @return the coursesTaught
	 */
	public List<Course> getCoursesTaught() {
		return coursesTaught;
	}

	/**
	 * @param coursesTaught the coursesTaught to set
	 */
	public void setCoursesTaught(List<Course> coursesTaught) {
		this.coursesTaught = coursesTaught;
	}

	public List<Attendance> getAttendance() {
		return this.attendance;
	}

	public boolean addCourseEnrolled(Course course) {
		if (this.coursesEnrolled == null) {
			this.coursesEnrolled = new ArrayList<Course>();
		}

		return this.coursesEnrolled.add(course);
	}

	public boolean dropCourseEnrolled(Course course) {
		if (this.coursesEnrolled == null) {
			return true;
		}

		return this.coursesEnrolled.remove(course);
	}

	public boolean markAttendance(Attendance attendance) {
		if (this.attendance == null) {
			this.attendance = new ArrayList<Attendance>();
		}

		return this.attendance.add(attendance);
	}

	public boolean dropAttendance(Attendance attendance) {
		if (this.attendance == null) {
			return true;
		}

		return this.attendance.remove(attendance);
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

	public void removeAttendance(Attendance attendance) {
		Course course = attendance.getCourse();
		LocalDateTime classTime = attendance.getClasstime();
		Iterator<Attendance> it = this.getAttendance().iterator();
		while (it.hasNext()) {
			Attendance thisAttendance = it.next();
			if (thisAttendance.getCourse().equals(course) && thisAttendance.getClasstime().equals(classTime)) {
				it.remove();
			}
		}
	}

	public AttendanceStatus getAttendanceStatus(Course course, LocalDateTime classtime) {
		Iterator<Attendance> it = this.getAttendance().iterator();
		while (it.hasNext()) {
			Attendance thisAttendance = it.next();
			if (thisAttendance.getCourse().equals(course) && thisAttendance.getClasstime().equals(classtime)) {
				return thisAttendance.getStatus();
			}
		}
		return null;
	}

	public boolean equals(Object obj) {
		if (!obj.getClass().equals(this.getClass())) {
			return false;
		} else {
			return this.username.equals(((User) obj).getUsername());
		}
	}

	@Override
	public int compareTo(User other) {
		int last = 0;
		if (other.lastName != null && this.lastName != null) {
			last = this.lastName.compareTo(other.lastName);
		}

		if (last != 0) {
			return last;
		} else {
			if (this.firstName != null && other.firstName != null) {
				return this.firstName.compareTo(other.firstName);
			} else {
				return 1;
			}
		}
	}

}
