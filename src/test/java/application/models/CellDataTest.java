/**
 * CellDataTest.java is part of the "SheeTXT" project (c) by Mark "Grandy" Bishop, 2020.
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
package application.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CellDataTest {

	@Test
	public void test_equality() {
		// Especially important as we hit this in {@link SheetCache} with Map keys

		CellData data1 = new CellData(1, 3);
		CellData data2 = new CellData("B4", "file");

		Assertions.assertEquals(1, data2.getCol());
		Assertions.assertEquals(3, data2.getRow());
		Assertions.assertTrue(data1.equals(data2), "Expected data1 to equal data2");
	}

	@Test
	public void test_constructor_fromCoord() {
		CellData data = new CellData("F7", "file");

		Assertions.assertEquals("F7", data.getCoordString());
		Assertions.assertEquals(5, data.getCol());
		Assertions.assertEquals(6, data.getRow());
	}

	@Test
	public void test_constructor_fromInts() {
		CellData data = new CellData(5, 6);

		Assertions.assertEquals("N/A", data.getCoordString()); // not calculated as not needed
		Assertions.assertEquals(5, data.getCol());
		Assertions.assertEquals(6, data.getRow());
	}
}