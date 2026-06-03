<script>
    const endpoints = ["weather", "cat"];
</script>

<section>
    <h1>Welcome Graysons Smart Display Dashboard</h1>
    <div>
        {#each endpoints as endpoint}
            <a href="./{endpoint}">
                <img src="./{endpoint}" />
                <span>/{endpoint}</span>
            </a>
        {/each}
    </div>
</section>

<style>
    h1 {
        width: 100%;
        margin-bottom: 2rem;
        text-align: center;
        font-weight: 100;
    }

    section {
        max-width: 100%;
        padding: 1em;
    }

    div {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 1em;
        width: 100%;
        max-width: 100%;
    }

    a {
        position: relative;
        display: block;
        width: 100%;
        overflow: hidden;
        aspect-ratio: 4/3;
        background-color: #333;
        border-radius: 0.5em;
    }

    img {
        max-width: 100%;
        border-radius: 0.5em;
    }

    span {
        font-family: "Courier New", Courier, monospace;
        position: absolute;
        color: white;
        text-decoration: none;
        bottom: 1em;
        right: 1em;
        background-color: #222;
        padding: 0.25em 0.75em;
        border-radius: 0.25em;
    }
</style>
