package com.example.online_shop_spring_boot.repository;

import com.example.online_shop_spring_boot.domains.Uploads;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadsRepository extends JpaRepository<Uploads,Long> {

}
