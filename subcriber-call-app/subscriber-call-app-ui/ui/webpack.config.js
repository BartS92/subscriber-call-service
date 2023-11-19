const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const TerserPlugin = require('terser-webpack-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const TsconfigPathsPlugin = require('tsconfig-paths-webpack-plugin');
const path = require('path');
const deps = require('./package.json').dependencies;

require('dotenv').config({ path: '.env' });

module.exports = (env, argv) => {
    return {
        entry: './src/index.tsx',
        mode: argv.mode || 'development',
        devtool: argv === 'production' ? null : 'source-map',
        devServer: {
            static: './dist',
            hot: true,
            port: 3000,
            historyApiFallback: true,
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH, OPTIONS',
                'Access-Control-Allow-Headers': 'X-Requested-With, content-type, Authorization'
            },
            proxy: {
                '/subscriber-call-app/service': {
                    target: 'http://localhost:3000',
                    router: () => 'http://localhost:8080',
                    logLevel: 'debug'
                }
            }
        },
        resolve: {
            extensions: ['.ts', '.tsx', '.js'],
            alias: {
                '@components': path.resolve(__dirname, 'src/components'),
                '@icons': path.resolve(__dirname, 'src/components/icons'),
                '@store': path.resolve(__dirname, 'src/store')
            }
        },
        optimization: {
            minimizer: [new TerserPlugin({
                extractComments: false,
                parallel: true,
                terserOptions: {
                    ecma: 5
                }
            })]
        },
        module: {
            rules: [
                {
                    test: /\.(js|jsx|tsx|ts)$/,
                    loader: 'ts-loader',
                    exclude: /node_modules/
                },
                {
                    test: /\.css$/i,
                    use: ['style-loader', 'css-loader', 'postcss-loader']
                },
                {
                    test: /\.(eot|ttf|woff|woff2|png|jpg|gif)$/i,
                    type: 'asset'
                },
                {
                    test: /\.svg$/i,
                    issuer: /\.[jt]sx?$/,
                    use: ['@svgr/webpack'],
                }
            ]
        },
        plugins: [
            new webpack.EnvironmentPlugin(),
            new webpack.DefinePlugin({
                'process.env': JSON.stringify(process.env)
            }),
            new HtmlWebpackPlugin({
                template: './public/index.html'
            }),
            new ForkTsCheckerWebpackPlugin(),
            new TsconfigPathsPlugin({
                extensions: ['.js', '.jsx', '.json', '.ts', '.tsx']
            })
        ]
    };
};
