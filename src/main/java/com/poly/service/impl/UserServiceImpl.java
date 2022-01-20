package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.domain.User;
import com.poly.repository.UserRepository;
import com.poly.service.UserService;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService{
		@Autowired
	UserRepository repository;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

		@Override
		public Optional<User> findById(String id) {
			return repository.findById(id);
		}

		@Override
		public List<User> findByFullnameContaining(String name) {
			return repository.findByFullnameContaining(name);
		}

		@Override
		public Page<User> findByFullnameContaining(String name, Pageable pageable) {
			return repository.findByFullnameContaining(name, pageable);
		}
		@Override
		public User findByEmailContaining(String email) {
		return repository.findByEmailContaining(email);
		}
		@Override
		public List<User> findByRoleTrueAndStatusTrue() {
		return repository.findByRoleTrueAndStatusTrue();
		}

		@Override
		public <S extends User> S save(S entity) {
			Optional<User> optsexist = findById(entity.getUsername());
			if(optsexist.isPresent()){
				if(StringUtils.isEmpty(entity.getPassword())){
					entity.setPassword(optsexist.get().getPassword());
				}else{
					entity.setPassword(passwordEncoder.encode(entity.getPassword()));
				}
			}else{
				entity.setPassword(passwordEncoder.encode(entity.getPassword()));
			}
			return repository.save(entity);
		}

		@Override
		public Page<User> findAll(Pageable pageable) {
			return repository.findAll(pageable);
		}

		@Override
		public List<User> findAll() {
			return repository.findAll();
		}

		@Override
		public List<User> findAll(Sort sort) {
			return repository.findAll(sort);
		}

		@Override
		public <S extends User> boolean exists(Example<S> example) {
			return repository.exists(example);
		}

		@Override
		public long count() {
			return repository.count();
		}

		@Override
		public void deleteById(String id) {
			repository.deleteById(id);
		}

		@Override
		public void delete(User entity) {
			repository.delete(entity);
		}

		@Override
		public void deleteAll() {
			repository.deleteAll();
		}
		
		
}
