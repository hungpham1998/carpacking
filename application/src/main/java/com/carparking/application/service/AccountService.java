package com.carparking.application.service;

import com.carparking.application.dto.AccountDto;
import com.carparking.application.exception.ResourceAlreadyExistsException;
import com.carparking.application.exception.ResourceNotFoundException;
import com.carparking.application.repository.AccountRepository;
import com.carparking.application.repository.RoleRepository;
import com.carparking.application.request.AccountRequest;
import com.carparking.application.response.ResponseObjectType;
import com.carparking.application.ultis.PageSizeObjectType;
import com.carparking.application.ultis.SortPageSize;
import com.carparking.core_entity.entities.Account;
import com.carparking.core_entity.entities.RoleModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private  final AccountRepository accountRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(AccountRequest accountR) {
        List<Account> listData = accountRepository.getListAccountByUserNameAndEmail(accountR.getUsername(),
                accountR.getEmail());
        if (listData.size() > 0) {
            throw new ResourceAlreadyExistsException("Data account already exist. Create account faild !");
        }
        Account account = new Account();
        BeanUtils.copyProperties(accountR, account);
        account.setPassword(bCryptPasswordEncoder.encode(accountR.getPassword()));
        List<RoleModel> listRole = new ArrayList<>();
        if (accountR.getListRole().size() > 0) {
            for (Long data : accountR.getListRole()) {
                RoleModel role = roleRepository.findById(data)
                        .orElseThrow(() -> new ResourceNotFoundException("Data role not founds"));
                listRole.add(role);
            }
        } else {
            RoleModel role = roleRepository.findByName("USER");
            listRole.add(role);
        }
        account.setRoles(listRole);
        return accountRepository.save(account);
    }

    public Account update(Long id, AccountRequest accountR) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (!accountOptional.isPresent()) {
            throw new ResourceNotFoundException("Account not found");
        } else {
            List<Account> listDataAccount = accountRepository
                    .getListAccountByUserNameAndEmailAndIdNot(accountR.getUsername(), accountR.getEmail(), id);
            if (listDataAccount.size() > 0) {
                throw new ResourceAlreadyExistsException("Data account already exist. Create account faild !");
            }
            Account account = new Account();
            BeanUtils.copyProperties(accountR, account);
            if (accountR.getPassword() != null || accountR.getPassword().length() > 0) {
                account.setPassword(bCryptPasswordEncoder.encode(accountR.getPassword()));
            } else {
                account.setPassword(accountOptional.get().getPassword());
            }

            List<RoleModel> listRole = new ArrayList<>();
            if (accountR.getListRole().size() > 0) {
                for (Long data : accountR.getListRole()) {
                    RoleModel role = roleRepository.findById(data)
                            .orElseThrow(() -> new ResourceNotFoundException("Data role not founds"));
                    listRole.add(role);
                }
            } else {
                RoleModel role = roleRepository.findByName("USER");
                listRole.add(role);
            }
            account.setId(id);
            return accountRepository.save(account);
        }
    }

    public void delete(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (!accountOptional.isPresent()) {
            throw new ResourceNotFoundException("Account not found !!!");
        } else {
            accountRepository.deleteById(id);
        }
    }


    public List<Account> getAll() {
        return accountRepository.findAll();
    }


    public ResponseObjectType<AccountDto> getPage(String keyword, int page, int size, String field, String sort) {
        String query = null;
        if (keyword.length() > 0 || keyword.trim().equals("") == false) {
            query = keyword;
        }
        Pageable pageSize = SortPageSize.getPageSize(page, size, field, sort);
        Page<Account> res = accountRepository.getPageSizeAndKeyword(query, pageSize);
        List<AccountDto> listData = res.getContent().stream().map((e) -> {
            AccountDto account = new AccountDto();
            BeanUtils.copyProperties(e, account);
            return account;
        }).collect(Collectors.toList());
        return PageSizeObjectType.getPageSize(listData, size, page, res.getTotalElements(), res.getTotalPages());
    }


    public Account findById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (!accountOptional.isPresent()) {
            throw new ResourceNotFoundException("Account not found");
        }
        return accountOptional.get();
    }

    static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String res = pattern.matcher(temp).replaceAll("");
        return (res.replaceAll("\\s", "")).replaceAll("đ", "d");
    }

}
