<script>
    import favicon from "$lib/assets/favicon.svg";

    let { children } = $props();
</script>

<svelte:head>
    <link rel="icon" href={favicon} />
</svelte:head>

<main>
    {@render children()}
</main>

<style>
    @font-face {
        font-family: "Mona Sans";
        src: url("/fonts/MonaSans/Light.ttf") format("truetype");
        font-weight: 200;
        font-style: normal;
        font-display: swap;
    }

    @font-face {
        font-family: "Mona Sans";
        src: url("/fonts/MonaSans/Regular.ttf") format("truetype");
        font-weight: 400;
        font-style: normal;
        font-display: swap;
    }

    @font-face {
        font-family: "Mona Sans";
        src: url("/fonts/MonaSans/Bold.ttf") format("truetype");
        font-weight: 700;
        font-style: normal;
        font-display: swap;
    }

    @font-face {
        font-family: "Mona Sans";
        src: url("/fonts/MonaSans/Black.ttf") format("truetype");
        font-weight: 900;
        font-style: normal;
        font-display: swap;
    }

    main {
        font-family: "Mona Sans";
        width: 100vw;
    }

    :global(*) {
        margin: 0;
        padding: 0;
    }
</style>
