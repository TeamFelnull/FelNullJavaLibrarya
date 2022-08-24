package dev.felnull.fnjl.io.resource;

import dev.felnull.fnjl.util.FNDataUtil;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.Objects;

@ApiStatus.Internal
public class ResourceEntryImpl implements ResourceEntry {
    private final String name;
    private final boolean directory;
    private final String scheme;
    private final String path;
    private final Class<?> clazz;

    public ResourceEntryImpl(String name, boolean directory, String scheme, String path, Class<?> clazz) {
        this.name = name;
        this.directory = directory;
        this.scheme = scheme;
        this.path = path;
        this.clazz = clazz;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull String getScheme() {
        return scheme;
    }

    @Override
    public boolean isDirectory() {
        return directory;
    }

    @Override
    public InputStream openInputStream() {
        if (isDirectory())
            throw new RuntimeException("directory");

        return FNDataUtil.resourceExtractor(clazz, path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceEntryImpl that = (ResourceEntryImpl) o;
        return directory == that.directory && Objects.equals(name, that.name) && Objects.equals(scheme, that.scheme) && Objects.equals(path, that.path) && Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, directory, scheme, path, clazz);
    }
}
