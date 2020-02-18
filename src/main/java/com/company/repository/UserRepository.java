package com.company.repository;

import com.company.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query(value = "SELECT * \n" +
            "FROM users\n" +
            "left JOIN user_role ON users.id=user_role.user_id\n" +
            "where user_role.roles='USER'",
            nativeQuery = true)
    List<User> findAllStudents();

    @Query(value = "SELECT  *\n" +
            "FROM users\n" +
            "left JOIN speciality_class ON users.speciality_class_id=speciality_class.id\n" +
            "where speciality_class.name='DOCTOR'",
            nativeQuery = true)
    List<User> findAllDoctorStudents();

    @Query(value = "SELECT  *\n" +
            "FROM users\n" +
            "left JOIN speciality_class ON users.speciality_class_id=speciality_class.id\n" +
            "where speciality_class.name='CHEMIST'",
            nativeQuery = true)
    List<User> findAllChemistStudents();

}