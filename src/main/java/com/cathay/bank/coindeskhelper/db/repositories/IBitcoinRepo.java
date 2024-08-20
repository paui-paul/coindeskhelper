package com.cathay.bank.coindeskhelper.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cathay.bank.coindeskhelper.db.entities.Bitcoin;

public interface IBitcoinRepo extends JpaRepository<Bitcoin, String> {

}
