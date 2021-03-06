package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CostumeInvoiceRepository;
import domain.CostumeInvoice;

@Component
@Transactional
public class StringToCostumeInvoiceConverter implements Converter<String, CostumeInvoice> {

	@Autowired
	CostumeInvoiceRepository costumeInvoiceRepository;

	@Override
	public CostumeInvoice convert(String text) {
		CostumeInvoice result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = costumeInvoiceRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}