package org.powernukkit.updater;

import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author joserobjr
 * @since 2021-10-24
 */
public class AllResourcesDownloader {
    private static final String CANONICAL_BLOCK_STATES_PATH = "src/main/resources/canonical_block_states.nbt";
    private static final String CANONICAL_BLOCK_STATES_URL =
            "https://github.com/pmmp/BedrockData/raw/master/canonical_block_states.nbt"
            //"https://github.com/CloudburstMC/Data/raw/master/block_palette.nbt"
            ;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static final Optional<String> USE_NUKKIT_RUNTIME_BLOCK_STATES_DAT =
            //Optional.of("https://github.com/CloudburstMC/Nukkit/raw/v1.18.30/src/main/resources/runtime_block_states.dat")
            Optional.empty()
            ;

    public static void main(String[] args) {
        /*
        Pre-requisites:
        - Make sure that ProxyPass is updated and working with the last Minecraft Bedrock Edition client
        - Make sure PocketMine has released their exports: https://github.com/pmmp/BedrockData
        - Run ProxyPass with export-data in config.yml set to true, the proxy pass must be
            pointing to a vanilla BDS server from https://www.minecraft.net/en-us/download/server/bedrock
        - Connect to the ProxyPass server with the last Minecraft Bedrock Edition client at least once
        - Adjust the path bellow if necessary for your machine
         */
        new AllResourcesDownloader().execute("../Bedrock-ProxyPass/run/data");
        System.out.println("OK");
    }

    private void execute(@SuppressWarnings("SameParameterValue") String pathProxyPassData) {
        downloadResources();
        copyProxyPassResources(pathProxyPassData);
    }

    private void downloadResources() {
        createCanonicalBlockStatesNbt();
        download("https://github.com/pmmp/BedrockData/raw/master/required_item_list.json",
                "src/test/resources/org/powernukkit/updater/dumps/pmmp/required_item_list.json");
    }

    @SneakyThrows
    private void createCanonicalBlockStatesNbt() {
        if (!USE_NUKKIT_RUNTIME_BLOCK_STATES_DAT.isPresent()) {
            download(CANONICAL_BLOCK_STATES_URL, CANONICAL_BLOCK_STATES_PATH);
            return;
        }
        Path runtimeBlockStatesFile = Files.createTempFile("runtime_block_states", ".dat");
        try {
            download(USE_NUKKIT_RUNTIME_BLOCK_STATES_DAT.get(), runtimeBlockStatesFile.toAbsolutePath().toString());
            try (InputStream fileInputStream = Files.newInputStream(runtimeBlockStatesFile);
                 BufferedInputStream inputStream = new BufferedInputStream(fileInputStream)
            ) {
                //noinspection unchecked
                List<CompoundTag> tags = ((ListTag<CompoundTag>) NBTIO.readTag(inputStream, ByteOrder.BIG_ENDIAN, false)).getAll();
                tags.forEach(tag-> tag.remove("id").remove("data"));
                tags.sort(Comparator.comparingInt(tag -> tag.getInt("runtimeId")));
                Assertions.assertEquals(tags.size() - 1, tags.get(tags.size() - 1).getInt("runtimeId"));
                System.out.println(tags);
                throw new InternalError(); // Aborting this idea. Cloudburst Nukkit's dat file lacks A LOT of states.
            }
        } finally {
            Files.deleteIfExists(runtimeBlockStatesFile);
        }
    }

    private void copyProxyPassResources(String pathProxyPassData) {
        copy(pathProxyPassData, "biome_definitions.dat", "src/main/resources/biome_definitions.dat");
        copy(pathProxyPassData, "entity_identifiers.dat", "src/main/resources/entity_identifiers.dat");
        copy(pathProxyPassData, "creativeitems.json", "src/test/resources/org/powernukkit/updater/dumps/proxypass/creativeitems.json");
        copy(pathProxyPassData, "runtime_item_states.json", "src/test/resources/org/powernukkit/updater/dumps/proxypass/runtime_item_states.json");
        copy(pathProxyPassData, "recipes.json", "src/test/resources/org/powernukkit/updater/dumps/proxypass/recipes.json");
    }

    @SneakyThrows
    private void copy(String path, String file, String into) {
        Files.copy(Paths.get(path).resolve(file), Paths.get(into), StandardCopyOption.REPLACE_EXISTING);
    }

    @SneakyThrows
    private void download(String url, String into) {
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        try(InputStream input = connection.getInputStream();
            OutputStream fos = new FileOutputStream(into);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
        ) {
            byte[] buffer = new byte[8*1024];
            int read;
            while ((read = input.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
