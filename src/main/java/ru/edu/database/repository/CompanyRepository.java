package ru.edu.database.repository;

import ru.edu.database.bpp.InjectBean;
import ru.edu.database.pool.ConnectionPool;

public class CompanyRepository {

  @InjectBean // сделаем свою реализацию пост процесса при создании бинов
  private ConnectionPool connectionPool;

}
