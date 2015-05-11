package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Assessment;

@Component
@Transactional
public class AssessmentToStringConverter implements Converter<Assessment, String> {

	@Override
	public String convert(Assessment entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
