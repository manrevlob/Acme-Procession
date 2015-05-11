package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RegistrationInvoiceRepository;
import domain.RegistrationInvoice;

@Component
@Transactional
public class StringToRegistrationInvoiceConverter implements Converter<String, RegistrationInvoice> {

	@Autowired
	RegistrationInvoiceRepository registrationInvoiceRepository;

	@Override
	public RegistrationInvoice convert(String text) {
		RegistrationInvoice result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = registrationInvoiceRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}