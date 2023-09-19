package com.dam.server.repositories;

import com.dam.server.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, String> {
}
