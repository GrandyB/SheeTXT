/**
 * JsonValidationException.java is part of the "SheetsIO" project (c) by Mark "Grandy" Bishop, 2020.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package application.exceptions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import application.models.json.Config;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Exception type for the validation of our json configs going awry.
 *
 * @author Mark "Grandy" Bishop
 */
@RequiredArgsConstructor
public class JsonValidationException extends Exception {
	private static final long serialVersionUID = 1L;

	@Getter
	private final Set<ConstraintViolation<Config>> violations;

	/**
	 * @return a nicely formatted summary of violations.
	 */
	public String getSummary() {
		Map<String, List<ConstraintViolation<Config>>> perItemErrors = new LinkedHashMap<>();

		violations.forEach(v -> {
			// Property path for the violation, e.g. cells[13].name
			String key = v.getPropertyPath().toString().split("\\.")[0];
			perItemErrors.computeIfAbsent(key, k -> new ArrayList<>()).add(v);
		});

		StringBuilder builder = new StringBuilder("Found the following issue(s) during json validation:\n\n");
		for (Map.Entry<String, List<ConstraintViolation<Config>>> entry : perItemErrors.entrySet()) {
			String prop = entry.getKey();
			builder.append(prop);
			builder.append(" has the following errors:\n");

			entry.getValue().forEach(cv -> {
				builder.append("\t");
				builder.append(cv.getMessage());
				builder.append(" - value: [");
				builder.append(cv.getInvalidValue());
				builder.append("]\n");
			});
			builder.append("\n");
		}
		return builder.toString();
	}
}
