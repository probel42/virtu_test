package ru.ibelan.test.backend.services;

import ru.ibelan.test.backend.entities.Contract;
import ru.ibelan.test.backend.graphql.input.ContractInput;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Сервис для работы с договорами
 */
public interface ContractService {

    /**
     * @return общее кол-во договоров
     */
    long getContractsNumber();

    /**
     * @return список договоров (пагинабельный)
     */
    List<Contract> getContracts(int page, int size);

    /**
     * @return договор по идентификатору
     */
    Contract getContract(String id);

    /**
     * Добавить/изменить договор
     * @return id договора
     */
    String addEditContract(ContractInput contract) throws CalcInsuranceException, ParseException, IOException;

    /**
     * Расчитать страховую премию
     * @return страховая премия
     */
    Float calcInsurancePremium(int insuranceAmount,
                               String realPropertyType,
                               Integer year,
                               Integer area,
                               String dateFrom,
                               String dateTo) throws ParseException, IOException;

    /**
     * @return типы недвижимости
     */
    List<String> getRealPropertyTypes() throws IOException;

    class CalcInsuranceException extends RuntimeException {
        public CalcInsuranceException(String message) {
            super(message);
        }
    }
}
