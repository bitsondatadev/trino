/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.tests.product.launcher.cli;

import com.google.common.collect.ImmutableList;
import io.airlift.airline.Arguments;
import io.airlift.airline.Option;
import org.testng.annotations.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

public class TestOptionsPrinter
{
    @Test
    public void shouldFormatOptions()
    {
        assertThat(OptionsPrinter.format(new Options("test", false, emptyList()))).isEqualTo("--value test");
        assertThat(OptionsPrinter.format(new Options("test", true, emptyList()))).isEqualTo("--value test \\\n--boolean");
    }

    @Test
    public void shouldSkipNullStrings()
    {
        assertThat(OptionsPrinter.format(new Options("", false, emptyList()))).isEqualTo("");
    }

    @Test
    public void shouldSkipEmptyArguments()
    {
        assertThat(OptionsPrinter.format(new Options("", false, emptyList()))).isEqualTo("");
    }

    @Test
    public void shouldFormatArguments()
    {
        assertThat(OptionsPrinter.format(new Options("", false, ImmutableList.of("hello", "world")))).isEqualTo("-- hello world");
    }

    @Test
    public void shouldFormatMultipleObjects()
    {
        Options first = new Options("first", false, ImmutableList.of("hello", "world"));
        Options second = new Options("second", false, ImmutableList.of("hello", "world"));

        assertThat(OptionsPrinter.format(first, second)).isEqualTo("--value first \\\n-- hello world \\\n--value second \\\n-- hello world");
    }

    private static class Options
    {
        @Option(name = "--value", title = "value", description = "Test value")
        public String value;

        @Option(name = "--boolean", title = "value-boolean", description = "Test value boolean")
        public boolean valueBoolean;

        @Arguments
        public List<String> arguments;

        public Options(String value, boolean valueBoolean, List<String> arguments)
        {
            this.value = value;
            this.valueBoolean = valueBoolean;
            this.arguments = arguments;
        }
    }
}
